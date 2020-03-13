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
    if (!Assert.has(value)) {
      return false;
    }

    Optional<Upholstery> euroStandard = EnumUtils.fromString(value, Upholstery.class);

    return euroStandard.isPresent();
  }
}
