package bg.autohouse.errors;

import bg.autohouse.web.models.response.ErrorMessage;
import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({MakerNotFoundException.class})
  public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request) {

    ErrorMessage errorMessage =
        ErrorMessage.builder().timestamp(new Date()).message(ex.getMessage()).build();

    return handleExceptionInternal(
        ex, errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
    ErrorMessage errorMessage =
        ErrorMessage.builder().timestamp(new Date()).message(ex.getMessage()).build();

    return handleExceptionInternal(
        ex, errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
