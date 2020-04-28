package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class StateValidatorTest {
  private StateValidator validator = new StateValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidState() {
      assertThat(isValid("new")).isTrue();
      assertThat(isValid("used")).isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidState() {
      assertThat(isValid("Not valid")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return validator.isValid(value, null);
  }
}
