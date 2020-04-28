package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FuelTypeValidatorTest {
  private FuelTypeValidator validator = new FuelTypeValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidFuelType() {
      assertThat(isValid("Diesel")).isTrue();
      assertThat(isValid("diesel")).isTrue();
      assertThat(isValid("Gasoline")).isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidFuelType() {
      assertThat(isValid("Not valid fuel")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return validator.isValid(value, null);
  }
}
