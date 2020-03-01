package bg.autohouse.errors;

import bg.autohouse.web.models.response.ApiResponse;
import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({MakerNotFoundException.class})
  public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request) {

    ApiResponse response =
        ApiResponse.builder()
            .success(Boolean.FALSE)
            .message(ex.getMessage())
            .timestamp(new Date())
            .status(HttpStatus.NOT_FOUND.getReasonPhrase())
            .build();

    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {

    ApiResponse response =
        ApiResponse.builder()
            .success(Boolean.FALSE)
            .message(ex.getMessage())
            .timestamp(new Date())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .build();

    return handleExceptionInternal(
        ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
