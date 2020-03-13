package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BodyStyleValidator implements ConstraintValidator<ValidBodyStyle, String> {

  @Override
  public boolean isValid(String bodyStyle, ConstraintValidatorContext context) {
    if (!Assert.has(bodyStyle)) {
      return false;
    }

    Optional<BodyStyle> euroStandard = EnumUtils.fromString(bodyStyle, BodyStyle.class);

    return euroStandard.isPresent();
  }
}
