package bg.autohouse.service.services;

import bg.autohouse.errors.RequiredFieldMissing;
import bg.autohouse.service.models.error.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;

public interface RestErrorService {
  RestError exposeGeneralException(Throwable ex);

  RestError exposeConstraintViolation(ConstraintViolationException cve);

  RestError exposeMethodArgumentError(MethodArgumentNotValidException ex, HttpStatus status);

  RestError exposeOtherSpringError(Exception ex, HttpStatus status);

  RestError exposeValidationError(RequiredFieldMissing rfe);
}
