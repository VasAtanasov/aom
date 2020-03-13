package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class NumberValidatorTest {
  private NumberValidator validator = new NumberValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidNumber() {
      assertThat(isValid(45)).isTrue();
      assertThat(isValid(4523)).isTrue();
      assertThat(isValid(44451)).isTrue();
      assertThat(isValid(945)).isTrue();
      assertThat(isValid(145)).isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidNumber() {
      assertThat(isValid(null)).isFalse();
      assertThat(isValid(-1)).isFalse();
    }
  }

  private boolean isValid(Integer value) {
    return validator.isValid(value, null);
  }
}
