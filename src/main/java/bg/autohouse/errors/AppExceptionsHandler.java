package bg.autohouse.errors;

import bg.autohouse.web.models.response.ApiResponseModel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {MakerNotFoundException.class})
  public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request) {

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.getReasonPhrase())
            .build();

    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message("Something went wrong.")
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .build();

    return handleExceptionInternal(
        ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    Map<String, String> errorMap = new HashMap<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errorMap.put(error.getField(), error.getDefaultMessage());
    }

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message("Validation Failed")
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errorMap)
            .build();

    return handleExceptionInternal(
        ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
