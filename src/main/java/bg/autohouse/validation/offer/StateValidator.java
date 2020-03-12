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
    if (!Assert.has(stateString)) {
      return false;
    }

    Optional<State> state = EnumUtils.fromString(stateString, State.class);

    return state.isPresent();
  }
}
