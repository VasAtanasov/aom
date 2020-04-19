package bg.autohouse.config;

import bg.autohouse.service.models.error.RestError;
import bg.autohouse.service.services.RestErrorService;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.response.ResponseWrapper;
import bg.autohouse.web.util.RestUtil;
import io.jsonwebtoken.JwtException;
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
    log.error("Exception caught", ex);
    final RestError restError = restErrorService.exposeGeneralException(ex);
    // String message = restError.getHttpStatus().getReasonPhrase();
    ResponseWrapper response = RestUtil.response(b -> b.message(RestMessage.SOMETHING_WENT_WRONG));
    return new ResponseEntity<>(response, restError.getHttpStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    log.error("Spring parameter validation failed", ex);
    final RestMessage message = RestMessage.PARAMETER_VALIDATION_FAILURE;
    final RestError restError = restErrorService.exposeMethodArgumentError(ex, status);
    ResponseWrapper response =
        RestUtil.response(b -> b.message(message).errors(restError.getErrors()));
    return handleExceptionInternal(ex, response, headers, status, request);
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity<ResponseWrapper> handleConstraintViolationException(
      final ConstraintViolationException cve) {
    log.error("Constraint violation failure", cve);
    final RestMessage message = RestMessage.CONSTRAINT_VIOLATION;
    final RestError restError = restErrorService.exposeConstraintViolation(cve);
    ResponseWrapper response =
        RestUtil.response(b -> b.message(message).errors(restError.getErrors()));
    return new ResponseEntity<>(response, restError.getHttpStatus());
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<ResponseWrapper> invalidTokenResponse() {
    return RestUtil.errorResponse(RestMessage.INVALID_TOKEN);
  }
}
