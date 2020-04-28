package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ColorValidatorTest {
  private ColorValidator validator = new ColorValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidColor() {
      assertThat(isValid("RED")).isTrue();
      assertThat(isValid("WHITE")).isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidColor() {
      assertThat(isValid("Not valid")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return validator.isValid(value, null);
  }
}
