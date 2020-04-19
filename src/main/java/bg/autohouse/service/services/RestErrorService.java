package bg.autohouse.service.services;

import bg.autohouse.service.models.error.RestError;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface RestErrorService {
  RestError exposeGeneralException(Throwable ex);

  RestError exposeConstraintViolation(ConstraintViolationException cve);

  RestError exposeMethodArgumentError(MethodArgumentNotValidException ex, HttpStatus status);
}
