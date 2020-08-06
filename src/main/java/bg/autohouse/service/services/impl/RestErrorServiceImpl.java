package bg.autohouse.service.services.impl;

import bg.autohouse.errors.MakerNotFoundException;
import bg.autohouse.errors.ModelNotFoundException;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.errors.RequiredFieldMissing;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.errors.modela.ValidationError;
import bg.autohouse.service.models.error.RestError;
import bg.autohouse.service.models.error.RestError.RestErrorBuilder;
import bg.autohouse.service.models.error.RestValidationError;
import bg.autohouse.service.services.RestErrorService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.F;
import bg.autohouse.web.enums.RestMessage;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
          .put(RequiredFieldMissing.class, HttpStatus.BAD_REQUEST)
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

  private static HttpStatus getHttpStatusCode(final Throwable ex) {
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
    RestErrorBuilder restErrorBuilder = RestError.builder().httpStatus(httpStatusCode);
    if (Assert.has(ex.getMessage())) {
      RestMessage restMessage =
          EnumUtils.fromString(ex.getMessage(), RestMessage.class)
              .orElse(RestMessage.SOMETHING_WENT_WRONG);
      restErrorBuilder.message(restMessage);
    }
    return restErrorBuilder.build();
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
    return RestError.builder()
        .httpStatus(HttpStatus.BAD_REQUEST)
        .message(RestMessage.CONSTRAINT_VIOLATION)
        .errors(errors)
        .build();
  }

  @Override
  public RestError exposeMethodArgumentError(
      MethodArgumentNotValidException ex, HttpStatus status) {
    Function<FieldError, RestValidationError> fieldErrorMapper =
        err ->
            RestValidationError.builder()
                .field(err.getField())
                .fieldErrorCode(err.getCode())
                .errorMessage(err.getDefaultMessage())
                .build();
    Function<ObjectError, RestValidationError> objectErrorMapper =
        err ->
            RestValidationError.builder()
                .field(err.getObjectName())
                .fieldErrorCode(err.getCode())
                .errorMessage(err.getDefaultMessage())
                .build();
    BindingResult result = ex.getBindingResult();
    List<RestValidationError> errors =
        Stream.concat(
                F.mapToStream(result.getFieldErrors(), fieldErrorMapper),
                F.mapToStream(result.getGlobalErrors(), objectErrorMapper))
            .collect(Collectors.toList());
    return RestError.builder()
        .httpStatus(status)
        .message(RestMessage.PARAMETER_VALIDATION_FAILURE)
        .errors(errors)
        .build();
  }

  public RestError exposeOtherSpringError(Exception ex, HttpStatus status) {
    return RestError.builder()
        .httpStatus(status)
        .message(
            EnumUtils.fromString(ex.getMessage(), RestMessage.class)
                .orElse(RestMessage.SOMETHING_WENT_WRONG))
        .build();
  }

  @Override
  public RestError exposeValidationError(RequiredFieldMissing rfe) {
    final HttpStatus httpStatusCode = getHttpStatusCode(rfe);
    final ValidationError error = rfe.getValidationError();
    return RestError.builder()
        .httpStatus(httpStatusCode)
        .message(RestMessage.CONSTRAINT_VIOLATION)
        .errors(
            Stream.of(rfe.getValidationError())
                .map(
                    err ->
                        RestValidationError.builder()
                            .field(error.getFieldName())
                            .errorMessage(error.getMessage())
                            .fieldErrorCode("Required")
                            .build())
                .collect(Collectors.toList()))
        .build();
  }

  // private List<RestValidationError> getValidationErrors(BindingResult result) {
  // Stream<RestValidationError> fieldErrorStream =
  //     result.getFieldErrors().stream().map(fieldErrorToValidationErrorModel);
  // Stream<RestValidationError> objectErrorStream =
  //     result.getGlobalErrors().stream().map(objectErrorToRestValidationError);
  // return Stream.concat(
  //         F.stream(result.getFieldErrors(), fieldErrorMapper),
  //         F.stream(result.getGlobalErrors(), objectErrorMapper))
  //     .collect(Collectors.toList());
  // }
}
