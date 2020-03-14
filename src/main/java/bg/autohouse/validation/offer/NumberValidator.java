package bg.autohouse.validation.offer;

import bg.autohouse.util.Assert;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<ValidNumber, Integer> {

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    return validate(value);
  }

  public static boolean validate(final Integer value) {
    return Assert.has(value) && value > 0;
  }
}
