package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EuroStandardValidatorTest {
  private EuroStandardValidator validator = new EuroStandardValidator();

  @Nested
  class EuroStandardValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidFuelType() {
      assertThat(isValid("Euro1")).isTrue();
      assertThat(isValid("Euro2")).isTrue();
      assertThat(isValid("Euro3")).isTrue();
      assertThat(isValid("Euro4")).isTrue();
      assertThat(isValid("Euro5")).isTrue();
      assertThat(isValid("Euro6")).isTrue();
    }
  }

  @Nested
  class EuroStandardInvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidFuelType() {
      assertThat(isValid("Not valid")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return validator.isValid(value, null);
  }
}
