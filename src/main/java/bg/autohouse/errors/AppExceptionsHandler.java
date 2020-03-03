package bg.autohouse.errors;

import bg.autohouse.errors.models.ErrorModel;
import bg.autohouse.errors.models.HttpMediaTypeErrorModel;
import bg.autohouse.errors.models.HttpRequestMethodErrorModel;
import bg.autohouse.errors.models.ValidationErrorModel;
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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

  // 400

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    Set<ValidationErrorModel> errors = getValidationErrors(ex.getBindingResult());

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message("Validation of one or more arguments failed.")
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errors)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
      final BindException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    Set<ValidationErrorModel> errors = getValidationErrors(ex.getBindingResult());

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errors)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  public ResponseEntity<Object> handleTypeMismatch(
      final TypeMismatchException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    logger.info(ex.getClass().getName());

    final String error =
        ex.getValue()
            + " value for "
            + ex.getPropertyName()
            + " should be of type "
            + ex.getRequiredType();

    final ErrorModel errorModel =
        ErrorModel.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .field("error", error)
            .build();

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errorModel)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      final MissingServletRequestPartException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    logger.info(ex.getClass().getName());
    //
    final String error = ex.getRequestPartName() + " part is missing";

    final ErrorModel errorModel =
        ErrorModel.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .field("error", error)
            .build();

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errorModel)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      final MissingServletRequestParameterException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    logger.info(ex.getClass().getName());
    //
    final String error = ex.getParameterName() + " parameter is missing";

    final ErrorModel errorModel =
        ErrorModel.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .field("error", error)
            .build();

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errorModel)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    logger.info(ex.getClass().getName());
    //
    final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

    final ErrorModel errorModel =
        ErrorModel.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .field("error", error)
            .build();

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errorModel)
            .build();

    return handleExceptionInternal(
        ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  // 404

  @ExceptionHandler(value = {MakerNotFoundException.class, ModelNotFoundException.class})
  public final ResponseEntity<Object> handleNotFoundExceptions(
      final ApiBaseException ex, final WebRequest request) {

    final ErrorModel errorModel =
        ErrorModel.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .field("error", ex.getMessage())
            .build();

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.NOT_FOUND.getReasonPhrase())
            .payload(errorModel)
            .build();

    return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      final NoHandlerFoundException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    logger.info(ex.getClass().getName());
    //
    final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

    final ErrorModel errorModel =
        ErrorModel.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .field("error", error)
            .build();

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.NOT_FOUND.getReasonPhrase())
            .payload(errorModel)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.NOT_FOUND, request);
  }

  // 405

  @Override
  public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      final HttpRequestMethodNotSupportedException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final HttpRequestMethodErrorModel httpRequestMethodErrorModel =
        HttpRequestMethodErrorModel.builder()
            .actualMethod(ex.getMethod())
            .supportedMethods(ex.getSupportedHttpMethods())
            .build();

    final ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(ex.getLocalizedMessage())
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(httpRequestMethodErrorModel)
            .build();

    return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
  }

  // 406

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

  // 415

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

  // 500

  @ExceptionHandler(value = {RuntimeException.class})
  public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
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

  private Set<ValidationErrorModel> getValidationErrors(BindingResult result) {
    return Stream.concat(
            result.getFieldErrors().stream()
                .map(
                    err ->
                        ValidationErrorModel.builder()
                            .errorCode(err.getCode())
                            .fieldName(err.getField())
                            .rejectedValue(err.getRejectedValue())
                            .params(collectArguments(err.getArguments()))
                            .message(err.getField() + ": " + err.getDefaultMessage())
                            .build()),
            result.getGlobalErrors().stream()
                .map(
                    err ->
                        ValidationErrorModel.builder()
                            .errorCode(err.getCode())
                            .params(collectArguments(err.getArguments()))
                            .message(err.getObjectName() + ": " + err.getDefaultMessage())
                            .build()))
        .collect(Collectors.toSet());
  }

  private List<String> collectArguments(Object[] arguments) {
    return arguments == null
        ? Collections.emptyList()
        : Stream.of(arguments).skip(1).map(Object::toString).collect(Collectors.toList());
  }
}
