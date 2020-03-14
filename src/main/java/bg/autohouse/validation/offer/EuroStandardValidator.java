package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.EuroStandard;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EuroStandardValidator implements ConstraintValidator<ValidEuroStandard, String> {

  @Override
  public boolean isValid(String euroStandardString, ConstraintValidatorContext context) {
    return validate(euroStandardString);
  }

  public static boolean validate(final String value) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<EuroStandard> euroStandard = EnumUtils.fromString(value, EuroStandard.class);

    return euroStandard.isPresent();
  }
}
