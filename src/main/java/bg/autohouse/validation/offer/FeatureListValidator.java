package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FeatureListValidator implements ConstraintValidator<ValidFeatureList, List<String>> {

  @Override
  public boolean isValid(List<String> values, ConstraintValidatorContext context) {
    if (!Assert.has(values)) {
      return false;
    }

    if (values.isEmpty()) {
      return false;
    }

    boolean allValid =
        values.stream()
            .allMatch(feature -> EnumUtils.fromString(feature, Feature.class).isPresent());

    return allValid;
  }
}
