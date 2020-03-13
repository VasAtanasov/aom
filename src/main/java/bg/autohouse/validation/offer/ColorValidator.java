package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.Color;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ColorValidator implements ConstraintValidator<ValidColor, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<Color> euroStandard = EnumUtils.fromString(value, Color.class);

    return euroStandard.isPresent();
  }
}
