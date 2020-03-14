package bg.autohouse.validation.offer;

import bg.autohouse.data.models.enums.State;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateValidator implements ConstraintValidator<ValidState, String> {

  @Override
  public boolean isValid(String stateString, ConstraintValidatorContext context) {
    return validate(stateString);
  }

  public static boolean validate(final String value) {
    if (!Assert.has(value)) {
      return false;
    }

    Optional<State> state = EnumUtils.fromString(value, State.class);

    return state.isPresent();
  }
}
