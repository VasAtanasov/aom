package bg.autohouse.errors;

import bg.autohouse.errors.models.ErrorModel;
import bg.autohouse.errors.models.HttpMediaTypeErrorModel;
import bg.autohouse.errors.models.HttpRequestMethodErrorModel;
import bg.autohouse.errors.models.ValidationErrorModel;
import bg.autohouse.util.Payload;
import bg.autohouse.util.Payload.PayloadBuilder;
import bg.autohouse.web.models.response.ApiResponseModel;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
    logger.error("Global error handler exception: ", ex);

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message("Something went wrong.")
            .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .build();

    return handleExceptionInternal(
        ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(value = {MakerNotFoundException.class})
  public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request) {

    final ErrorModel errorModel =
        ErrorModel.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .field("error", ex.getMessage())
            .build();

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message("Resource not found!")
            .status(HttpStatus.NOT_FOUND.getReasonPhrase())
            .payload(errorModel)
            .build();

    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    PayloadBuilder payload = Payload.builder();

    ex.getBindingResult().getFieldErrors().stream()
        .forEach(err -> payload.field(err.getField(), err.getDefaultMessage()));

    Set<ValidationErrorModel> errors =
        Stream.concat(
                ex.getBindingResult().getFieldErrors().stream()
                    .map(
                        err ->
                            ValidationErrorModel.builder()
                                .errorCode(err.getCode())
                                .fieldName(err.getField())
                                .rejectedValue(err.getRejectedValue())
                                .params(collectArguments(err.getArguments()))
                                .message(err.getDefaultMessage())
                                .build()),
                ex.getBindingResult().getGlobalErrors().stream()
                    .map(
                        err ->
                            ValidationErrorModel.builder()
                                .errorCode(err.getCode())
                                .params(collectArguments(err.getArguments()))
                                .message(err.getDefaultMessage())
                                .build()))
            .collect(Collectors.toSet());

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errors)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
  }

  private List<String> collectArguments(Object[] arguments) {
    return arguments == null
        ? Collections.emptyList()
        : Stream.of(arguments).skip(1).map(Object::toString).collect(Collectors.toList());
  }

  @Override
  public ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    logger.info(ex.getClass().getName());

    final String error = "Parameter should be of type " + ex.getRequiredType().getName();

    final ErrorModel errorModel =
        ErrorModel.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .field("error", error)
            .build();

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errorModel)
            .build();

    return handleExceptionInternal(
        ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      final HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final HttpRequestMethodErrorModel httpRequestMethodErrorModel =
        HttpRequestMethodErrorModel.builder()
            .actualMethod(ex.getMethod())
            .supportedMethods(ex.getSupportedHttpMethods())
            .build();

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(httpRequestMethodErrorModel)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  public ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final HttpMediaTypeErrorModel httpMediaTypeErrorModel =
        HttpMediaTypeErrorModel.builder().mediaType(ex.getSupportedMediaTypes()).build();

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
            .payload(Collections.singleton(httpMediaTypeErrorModel))
            .build();

    return handleExceptionInternal(
        ex, response, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
  }

  @Override
  public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      HttpMediaTypeNotAcceptableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final HttpMediaTypeErrorModel httpMediaTypeErrorModel =
        HttpMediaTypeErrorModel.builder().mediaType(ex.getSupportedMediaTypes()).build();

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase())
            .payload(Collections.singleton(httpMediaTypeErrorModel))
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.NOT_ACCEPTABLE, request);
  }
}
