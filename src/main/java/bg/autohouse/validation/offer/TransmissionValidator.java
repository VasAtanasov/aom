package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.Transmission;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransmissionValidator implements ConstraintValidator<ValidTransmission, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return validate(value);
  }

  public static boolean validate(final String value) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<Transmission> state = EnumUtils.fromString(value, Transmission.class);

    return state.isPresent();
  }
}
