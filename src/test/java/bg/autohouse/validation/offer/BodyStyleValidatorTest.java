package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BodyStyleValidatorTest {
  private BodyStyleValidator validator = new BodyStyleValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidBodyStyle() {
      assertThat(isValid("SUV")).isTrue();
      assertThat(isValid("CONVERTIBLE")).isTrue();
      assertThat(isValid("PICKUP")).isTrue();
      assertThat(isValid("SEDAN")).isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidBodyStyle() {
      assertThat(isValid("Not valid")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return validator.isValid(value, null);
  }
}
