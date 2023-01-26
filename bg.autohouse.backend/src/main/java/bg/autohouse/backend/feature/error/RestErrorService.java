package bg.autohouse.backend.feature.error;

import bg.autohouse.backend.feature.RuntimeEnvironmentUtil;
import bg.autohouse.util.common.logging.ApplicationLoggerFactory;
import bg.autohouse.util.common.logging.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestErrorService {
  private static final Logger LOG = ApplicationLoggerFactory.getLogger(RestErrorService.class);
  private static final Map<Class<?>, HttpStatus> exceptionToStatusMapping = new HashMap<>();

  static {
    exceptionToStatusMapping.put(ValidationException.class, HttpStatus.BAD_REQUEST);
    exceptionToStatusMapping.put(NotFoundException.class, HttpStatus.NOT_FOUND);
    exceptionToStatusMapping.put(EntityNotFoundException.class, HttpStatus.NOT_FOUND);
    exceptionToStatusMapping.put(VersionConflictException.class, HttpStatus.CONFLICT);
    exceptionToStatusMapping.put(OptimisticLockingFailureException.class, HttpStatus.CONFLICT);
    //     exceptionToStatusMapping        .put(AccessDeniedException.class, HttpStatus.FORBIDDEN);
    exceptionToStatusMapping.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
    exceptionToStatusMapping.put(IllegalStateException.class, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  protected final RuntimeEnvironmentUtil runtimeEnvironmentUtil;

  public RestErrorService(RuntimeEnvironmentUtil runtimeEnvironmentUtil) {
    this.runtimeEnvironmentUtil = runtimeEnvironmentUtil;
  }

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

    LOG.warn("Unknown exception type: " + ex.getClass().getName());

    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

  public RestError exposeGeneralException(final Throwable ex) {
    final HttpStatus httpStatusCode = getHttpStatusCode(ex);

    final RestError.Builder builder = new RestError.Builder(httpStatusCode);

    if (shouldExposeExceptionMessage(ex) && StringUtils.hasText(ex.getMessage())) {
      builder.setMessage(ex.getMessage());
    } else {
      builder.setMessage(httpStatusCode.getReasonPhrase());
    }

    return builder.build();
  }

  private static boolean shouldExposeExceptionMessage(final Throwable ex) {
    return ex instanceof MessageExposableValidationException
        || ex instanceof NotFoundException
        || ex instanceof VersionConflictException;
  }

  public RestError exposeConstraintViolation(final ConstraintViolationException cve) {
    final RestError.Builder builder =
        new RestError.Builder(HttpStatus.BAD_REQUEST).setMessage("Constraint violation");

    if (runtimeEnvironmentUtil.isProductionEnvironment()) {
      return builder.build();
    }

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

      builder.validationError(fieldName, fieldErrorCode, fieldErrorMessage);
    }

    return builder.build();
  }

  public RestError exposeMethodArgumentError(
      final MethodArgumentNotValidException ex, final HttpStatus status) {
    final RestError.Builder builder = new RestError.Builder(status).setMessage("Invalid request");

    for (final ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
      builder.validationError(
          objectError.getObjectName(), objectError.getCode(), objectError.getDefaultMessage());
    }

    for (final FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      builder.validationError(
          fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
    }

    return builder.build();
  }

  public RestError exposeOtherSpringError(
      final Exception ex, final HttpStatus status, final WebRequest request) {
    return new RestError.Builder(status).setMessage(status.getReasonPhrase()).build();
  }
}
