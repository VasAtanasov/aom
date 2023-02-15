package bg.autohouse.backend.feature.error;

import bg.autohouse.backend.util.common.logging.ApplicationLoggerFactory;
import bg.autohouse.backend.util.common.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {
    private static final Logger LOG =
            ApplicationLoggerFactory.getLogger(ControllerExceptionAdvice.class);

    private final RestErrorService restErrorService;

    public ControllerExceptionAdvice(RestErrorService restErrorService) {
        this.restErrorService = restErrorService;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleAll(final Throwable ex) {
        LOG.error("Exception caught", ex);

        final RestError restError = restErrorService.exposeGeneralException(ex);

        return new ResponseEntity<>(restError, restError.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest request) {
        LOG.error("Spring MVC exception was caught", ex);

//    final RestError restError = restErrorService.exposeOtherSpringError(ex, statusCode, request);

//    return new ResponseEntity<>(restError, headers, restError.getHttpStatus());

        return handleExceptionInternal(ex, body, headers, statusCode, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request) {
        LOG.error("Spring parameter validation failed", ex);
//    final RestError restError = restErrorService.exposeMethodArgumentError(ex, statusCode);
//    return new ResponseEntity<>(restError, headers, restError.getHttpStatus());
        return super.handleMethodArgumentNotValid(ex, headers, statusCode, request);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<RestError> handleConstraintViolationException(
            final ConstraintViolationException cve) {
        LOG.error("Constraint violation failure", cve);
        final RestError restError = restErrorService.exposeConstraintViolation(cve);
        return new ResponseEntity<>(restError, restError.getHttpStatus());
    }
}
