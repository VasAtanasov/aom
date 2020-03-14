package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.Upholstery;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpholsteryValidator implements ConstraintValidator<ValidUpholstery, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return validate(value);
  }

  public static boolean validate(final String value) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<Upholstery> upholstery = EnumUtils.fromString(value, Upholstery.class);

    return upholstery.isPresent();
  }
}
