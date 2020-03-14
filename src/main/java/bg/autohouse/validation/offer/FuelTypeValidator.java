package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FuelTypeValidator implements ConstraintValidator<ValidFuelType, String> {

  @Override
  public boolean isValid(String fuelTypeString, ConstraintValidatorContext context) {
    return validate(fuelTypeString);
  }

  public static boolean validate(final String value) {
    if (!Assert.has(value)) {
      return false;
    }
    Optional<FuelType> fuelType = EnumUtils.fromString(value, FuelType.class);

    return fuelType.isPresent();
  }
}
