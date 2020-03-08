package bg.autohouse;

import static org.junit.Assert.*;

import java.util.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public abstract class HibernateValidatorTest {

  protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  protected <T> void isValid(T target) {
    assertTrue(validator.validate(target).isEmpty());
  }

  protected <T> void isInvalid(T target) {
    assertFalse(validator.validate(target).isEmpty());
  }

  protected <T> Map<String, List<String>> getViolationMessages(T target) {
    Map<String, List<String>> messages = new HashMap<>();
    Set<ConstraintViolation<T>> violations = validator.validate(target);

    violations.forEach(
        v -> {
          String field = v.getPropertyPath().toString();
          String message = v.getMessage();
          if (!messages.containsKey(field)) {
            messages.put(field, new ArrayList<>());
          }

          messages.get(field).add(message);
        });

    return messages;
  }

  protected <T> void assertMessage(T target, String field, String message) {
    Map<String, List<String>> violations = getViolationMessages(target);

    if (!violations.containsKey(field)) {
      fail(String.format("Field \"%s\" doesn't exist or doesn't have any errors", field));
    }

    if (!violations.get(field).contains(message)) {
      fail(String.format("Field \"%s\" doesn't have error message: %s", field, message));
    }

    assertTrue(violations.get(field).contains(message));
  }
}
