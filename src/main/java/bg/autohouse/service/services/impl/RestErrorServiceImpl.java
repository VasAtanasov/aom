package bg.autohouse.service.services.impl;

import bg.autohouse.errors.MakerNotFoundException;
import bg.autohouse.errors.ModelNotFoundException;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.service.models.error.RestError;
import bg.autohouse.service.models.error.RestValidationError;
import bg.autohouse.service.services.RestErrorService;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@Service
public class RestErrorServiceImpl implements RestErrorService {
  private static final ImmutableMap<Class<?>, HttpStatus> exceptionToStatusMapping =
      ImmutableMap.<Class<?>, HttpStatus>builder()
          .put(ValidationException.class, HttpStatus.BAD_REQUEST)
          .put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST)
          .put(BindException.class, HttpStatus.BAD_REQUEST)
          .put(TypeMismatchException.class, HttpStatus.BAD_REQUEST)
          .put(MissingServletRequestPartException.class, HttpStatus.BAD_REQUEST)
          .put(MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST)
          .put(MethodArgumentTypeMismatchException.class, HttpStatus.BAD_REQUEST)
          .put(HttpRequestMethodNotSupportedException.class, HttpStatus.BAD_REQUEST)
          .put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST)
          .put(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST)
          .put(NotFoundException.class, HttpStatus.NOT_FOUND)
          .put(EntityNotFoundException.class, HttpStatus.NOT_FOUND)
          .put(NoHandlerFoundException.class, HttpStatus.NOT_FOUND)
          .put(MakerNotFoundException.class, HttpStatus.NOT_FOUND)
          .put(ModelNotFoundException.class, HttpStatus.NOT_FOUND)
          .put(HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE)
          .put(HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
          .put(ResourceAlreadyExistsException.class, HttpStatus.CONFLICT)
          .put(OptimisticLockingFailureException.class, HttpStatus.CONFLICT)
          .put(AccessDeniedException.class, HttpStatus.FORBIDDEN)
          .put(IllegalStateException.class, HttpStatus.INTERNAL_SERVER_ERROR)
          .build();

  private static HttpStatus getHttpStatusCode(final @Nonnull Throwable ex) {
    final ResponseStatus annotationStatusCode =
        AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

    if (annotationStatusCode != null) {
      return annotationStatusCode.value();
    }

    for (final Map.Entry<Class<?>, HttpStatus> entry : exceptionToStatusMapping.entrySet()) {
      if (entry.getKey().isAssignableFrom(ex.getClass())) {
        return entry.getValue();
      }
    }

    log.warn("Unknown exception type: " + ex.getClass().getName());

    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

  @Override
  public RestError exposeGeneralException(final Throwable ex) {
    final HttpStatus httpStatusCode = getHttpStatusCode(ex);
    return RestError.builder().httpStatus(httpStatusCode).build();
  }

  @Override
  public RestError exposeConstraintViolation(ConstraintViolationException cve) {

    List<RestValidationError> errors = new ArrayList<>();
    for (final ConstraintViolation<?> violation : cve.getConstraintViolations()) {
      final String fieldName =
          String.format(
              "%s.%s", violation.getRootBeanClass().getSimpleName(), violation.getPropertyPath());
      final String fieldErrorCode =
          violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
      final String fieldErrorMessage;

      if (violation.getMessage() != null && !violation.getMessage().matches("^\\{.+\\}$")) {
        fieldErrorMessage =
            String.format(
                "%s, was %s",
                violation.getMessage(), StringUtils.quoteIfString(violation.getInvalidValue()));
      } else {
        fieldErrorMessage = "";
      }

      RestValidationError error =
          RestValidationError.builder()
              .field(fieldName)
              .fieldErrorCode(fieldErrorCode)
              .errorMessage(fieldErrorMessage)
              .build();

      errors.add(error);
    }

    return RestError.builder().httpStatus(HttpStatus.BAD_REQUEST).errors(errors).build();
  }

  @Override
  public RestError exposeMethodArgumentError(
      MethodArgumentNotValidException ex, HttpStatus status) {
    List<RestValidationError> errors = getValidationErrors(ex.getBindingResult());
    return RestError.builder().errors(errors).build();
  }

  private Function<FieldError, RestValidationError> fieldErrorToValidationErrorModel =
      err ->
          RestValidationError.builder()
              .field(err.getField())
              .fieldErrorCode(err.getCode())
              .errorMessage(err.getDefaultMessage())
              .build();

  private Function<ObjectError, RestValidationError> objectErrorToRestValidationError =
      err ->
          RestValidationError.builder()
              .field(err.getObjectName())
              .fieldErrorCode(err.getCode())
              .errorMessage(err.getDefaultMessage())
              .build();

  private List<RestValidationError> getValidationErrors(BindingResult result) {
    Stream<RestValidationError> fieldErrorStream =
        result.getFieldErrors().stream().map(fieldErrorToValidationErrorModel);
    Stream<RestValidationError> objectErrorStream =
        result.getGlobalErrors().stream().map(objectErrorToRestValidationError);
    return Stream.concat(fieldErrorStream, objectErrorStream).collect(Collectors.toList());
  }
}
