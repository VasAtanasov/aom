package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.Drive;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DriveValidator implements ConstraintValidator<ValidDrive, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return validate(value);
  }

  public static boolean validate(final String value) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<Drive> state = EnumUtils.fromString(value, Drive.class);

    return state.isPresent();
  }
}
