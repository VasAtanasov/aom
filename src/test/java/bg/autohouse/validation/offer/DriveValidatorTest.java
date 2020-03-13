package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DriveValidatorTest {
  private DriveValidator validator = new DriveValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidDrive() {
      assertThat(isValid("FRONT_WHEEL_DRIVE")).isTrue();
      assertThat(isValid("REAR_WHEEL_DRIVE")).isTrue();
      assertThat(isValid("FOUR_WHEEL_DRIVE")).isTrue();
      assertThat(isValid("ALL_WHEEL_DRIVE")).isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidDrive() {
      assertThat(isValid("Not valid")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return validator.isValid(value, null);
  }
}
