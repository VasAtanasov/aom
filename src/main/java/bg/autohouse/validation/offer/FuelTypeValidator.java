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
    if (!Assert.has(fuelTypeString)) {
      return false;
    }
    Optional<FuelType> fuelType = EnumUtils.fromString(fuelTypeString, FuelType.class);

    return fuelType.isPresent();
  }
}
