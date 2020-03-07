package bg.autohouse.errors;

import bg.autohouse.errors.models.HttpMediaTypeErrorModel;
import bg.autohouse.errors.models.HttpRequestMethodErrorModel;
import bg.autohouse.errors.models.ValidationErrorModel;
import bg.autohouse.util.Assert;
import bg.autohouse.web.models.response.ApiResponseModel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

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
          .put(NotFoundException.class, HttpStatus.NOT_FOUND)
          .put(EntityNotFoundException.class, HttpStatus.NOT_FOUND)
          .put(NoHandlerFoundException.class, HttpStatus.NOT_FOUND)
          .put(MakerNotFoundException.class, HttpStatus.NOT_FOUND)
          .put(ModelNotFoundException.class, HttpStatus.NOT_FOUND)
          .put(HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE)
          .put(HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
          .put(ResourceAlreadyExistsException.class, HttpStatus.CONFLICT)
          .put(OptimisticLockingFailureException.class, HttpStatus.CONFLICT)
          // TODO when add spring security
          // .put(AccessDeniedException.class, HttpStatus.FORBIDDEN)
          .put(IllegalStateException.class, HttpStatus.INTERNAL_SERVER_ERROR)
          .build();

  private static final ImmutableList<Class<?>> customExceptions =
      ImmutableList.<Class<?>>builder()
          .add(MakerNotFoundException.class)
          .add(ModelNotFoundException.class)
          .add(NotFoundException.class)
          .add(ResourceAlreadyExistsException.class)
          .build();

  // 400

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    Set<ValidationErrorModel> errors = getValidationErrors(ex.getBindingResult());

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final ApiResponseModel response =
        exposeApiResponseErrorModelWithPayLoad(ex, httpStatus, errors);

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
      final BindException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    Set<ValidationErrorModel> errors = getValidationErrors(ex.getBindingResult());

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final ApiResponseModel response =
        exposeApiResponseErrorModelWithPayLoad(ex, httpStatus, errors);

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  @Override
  public ResponseEntity<Object> handleTypeMismatch(
      final TypeMismatchException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final String error =
        ex.getValue()
            + " value for "
            + ex.getPropertyName()
            + " should be of type "
            + ex.getRequiredType();

    final ApiResponseModel response = exposeApiResponseErrorModelWithMessage(ex, httpStatus, error);

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      final MissingServletRequestPartException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final String error = ex.getRequestPartName() + " part is missing";

    final ApiResponseModel response = exposeApiResponseErrorModelWithMessage(ex, httpStatus, error);

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      final MissingServletRequestParameterException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final String error = ex.getParameterName() + " parameter is missing";

    final ApiResponseModel response = exposeApiResponseErrorModelWithMessage(ex, httpStatus, error);

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      final NoHandlerFoundException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {
    logger.info(ex.getClass().getName());

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

    final ApiResponseModel response = exposeApiResponseErrorModelWithMessage(ex, httpStatus, error);

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  // 405
  // TODO Fix the information given from the exception is not error information
  @Override
  public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      final HttpRequestMethodNotSupportedException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final HttpRequestMethodErrorModel httpRequestMethodErrorModel =
        HttpRequestMethodErrorModel.builder()
            .actualMethod(ex.getMethod())
            .supportedMethods(ex.getSupportedHttpMethods())
            .build();

    final ApiResponseModel response =
        exposeApiResponseErrorModelWithPayLoad(ex, httpStatus, httpRequestMethodErrorModel);

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  // 406
  // TODO Fix the information given from the exception is not error information
  @Override
  public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      final HttpMediaTypeNotAcceptableException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final HttpMediaTypeErrorModel httpMediaTypeErrorModel =
        HttpMediaTypeErrorModel.builder().mediaType(ex.getSupportedMediaTypes()).build();

    ApiResponseModel response =
        exposeApiResponseErrorModelWithPayLoad(ex, httpStatus, httpMediaTypeErrorModel);

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  // 415
  // TODO Fix the information given from the exception is not error information
  @Override
  public ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      final HttpMediaTypeNotSupportedException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request) {

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final HttpMediaTypeErrorModel httpMediaTypeErrorModel =
        HttpMediaTypeErrorModel.builder().mediaType(ex.getSupportedMediaTypes()).build();

    ApiResponseModel response =
        exposeApiResponseErrorModelWithPayLoad(
            ex, httpStatus, Collections.singleton(httpMediaTypeErrorModel));

    return handleExceptionInternal(ex, response, headers, httpStatus, request);
  }

  // 500

  @ExceptionHandler(value = {Throwable.class})
  public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {

    logger.error("Global error handler exception: ", ex);
    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final ApiResponseModel response =
        exposeApiResponseErrorModelWithMessage(ex, httpStatus, "Something went wrong.");

    return handleExceptionInternal(ex, response, new HttpHeaders(), httpStatus, request);
  }

  // Custom exceptions

  @ExceptionHandler(
      value = {
        MakerNotFoundException.class,
        ModelNotFoundException.class,
        NotFoundException.class,
        ResourceAlreadyExistsException.class
      })
  public final ResponseEntity<Object> handleNotFoundExceptions(
      final Exception ex, final WebRequest request) {

    final HttpStatus httpStatus = getHttpStatusCode(ex);

    final ApiResponseModel response = exposeApiResponseErrorModel(ex, httpStatus);

    return handleExceptionInternal(ex, response, new HttpHeaders(), httpStatus, request);
  }

  public ApiResponseModel exposeApiResponseErrorModel(
      final Throwable ex, final HttpStatus httpStatus) {
    return exposeApiResponseErrorModelInternal(ex, httpStatus, null, null);
  }

  public ApiResponseModel exposeApiResponseErrorModelWithMessage(
      final Throwable ex, final HttpStatus httpStatus, final String message) {
    return exposeApiResponseErrorModelInternal(ex, httpStatus, null, message);
  }

  public ApiResponseModel exposeApiResponseErrorModelWithPayLoad(
      final Throwable ex, HttpStatus httpStatus, Object payload) {
    return exposeApiResponseErrorModelInternal(ex, httpStatus, payload, null);
  }

  private ApiResponseModel exposeApiResponseErrorModelInternal(
      final Throwable ex, final HttpStatus httpStatus, final Object payload, final String message) {

    final ApiResponseModel.ApiResponseModelBuilder builder =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .status(httpStatus.value())
            .errors(payload);

    if (Assert.has(message)) {
      builder.message(message);
    } else if (shouldExposeExceptionMessage(ex) && Assert.has(ex.getMessage())) {
      builder.message(ex.getMessage());
    } else {
      builder.message(httpStatus.getReasonPhrase());
    }

    return builder.build();
  }

  private Function<FieldError, ValidationErrorModel> fieldErrorToValidationErrorModel =
      err ->
          ValidationErrorModel.builder()
              .errorCode(err.getCode())
              .fieldName(err.getField())
              .rejectedValue(err.getRejectedValue())
              .params(collectArguments(err.getArguments()))
              .message(err.getField() + ": " + err.getDefaultMessage())
              .build();

  private Function<ObjectError, ValidationErrorModel> objectErrorToValidationErrorModel =
      err ->
          ValidationErrorModel.builder()
              .errorCode(err.getCode())
              .params(collectArguments(err.getArguments()))
              .message(err.getObjectName() + ": " + err.getDefaultMessage())
              .build();

  private Set<ValidationErrorModel> getValidationErrors(BindingResult result) {
    Stream<ValidationErrorModel> fieldErrorStream =
        result.getFieldErrors().stream().map(fieldErrorToValidationErrorModel);
    Stream<ValidationErrorModel> objectErrorStream =
        result.getGlobalErrors().stream().map(objectErrorToValidationErrorModel);
    return Stream.concat(fieldErrorStream, objectErrorStream).collect(Collectors.toSet());
  }

  private List<String> collectArguments(Object[] arguments) {
    return arguments == null
        ? Collections.emptyList()
        : Stream.of(arguments).skip(1).map(Object::toString).collect(Collectors.toList());
  }

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

  private static boolean shouldExposeExceptionMessage(final @Nonnull Throwable ex) {
    return customExceptions.contains(ex.getClass());
  }
}
