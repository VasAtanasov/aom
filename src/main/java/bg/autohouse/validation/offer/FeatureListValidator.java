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
    return validate(values);
  }

  public static boolean validate(final List<String> values) {
    if (!Assert.has(values)) {
      return false;
    }

    boolean allValid =
        values.stream()
            .allMatch(feature -> EnumUtils.fromString(feature, Feature.class).isPresent());

    return allValid;
  }
}
