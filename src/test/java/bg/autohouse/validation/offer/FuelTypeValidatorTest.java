package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FuelTypeValidatorTest {
  private FuelTypeValidator fuelTypeValidator = new FuelTypeValidator();

  @Nested
  class FuelTypeValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidFuelType() {
      assertThat(isValid("Diesel")).isTrue();
      assertThat(isValid("diesel")).isTrue();
      assertThat(isValid("Gasoline")).isTrue();
    }
  }

  @Nested
  class FuelTypeInvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidFuelType() {
      assertThat(isValid("Not valid fuel")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return fuelTypeValidator.isValid(value, null);
  }
}
