package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FeatureValidator implements ConstraintValidator<ValidFeature, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<Feature> state = EnumUtils.fromString(value, Feature.class);

    return state.isPresent();
  }
}
