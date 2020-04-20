package bg.autohouse.config;

import bg.autohouse.errors.RequiredFieldMissing;
import bg.autohouse.service.models.error.RestError;
import bg.autohouse.service.services.RestErrorService;
import bg.autohouse.web.models.response.ResponseWrapper;
import bg.autohouse.web.util.RestUtil;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

  @Autowired private RestErrorService restErrorService;

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Object> handleAll(final Throwable ex) {
    log.error("Exception caught");
    final RestError restError = restErrorService.exposeGeneralException(ex);
    ResponseWrapper response = RestUtil.wrapper(b -> b.message(restError.getMessage()));
    return new ResponseEntity<>(response, restError.getHttpStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    log.error("Spring parameter validation failed");
    final RestError restError = restErrorService.exposeMethodArgumentError(ex, status);
    ResponseWrapper response =
        RestUtil.wrapper(b -> b.message(restError.getMessage()).errors(restError.getErrors()));
    return new ResponseEntity<>(response, headers, restError.getHttpStatus());
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      final Exception ex,
      final Object body,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    log.error("Spring exception was caught");

    final RestError restError = restErrorService.exposeOtherSpringError(ex, status);
    ResponseWrapper response = RestUtil.wrapper(b -> b.message(restError.getMessage()));
    return new ResponseEntity<>(response, headers, restError.getHttpStatus());
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity<ResponseWrapper> handleConstraintViolationException(
      final ConstraintViolationException cve) {
    log.error("Constraint violation failure");
    final RestError restError = restErrorService.exposeConstraintViolation(cve);
    ResponseWrapper response =
        RestUtil.wrapper(b -> b.message(restError.getMessage()).errors(restError.getErrors()));
    return new ResponseEntity<>(response, restError.getHttpStatus());
  }

  @ExceptionHandler(value = RequiredFieldMissing.class)
  public ResponseEntity<ResponseWrapper> handleRequiredFieldMissing(
      final RequiredFieldMissing rfe) {
    log.error("Constraint violation failure");
    final RestError restError = restErrorService.exposeValidationError(rfe);
    ResponseWrapper response =
        RestUtil.wrapper(b -> b.message(restError.getMessage()).errors(restError.getErrors()));
    return new ResponseEntity<>(response, restError.getHttpStatus());
  }
}
