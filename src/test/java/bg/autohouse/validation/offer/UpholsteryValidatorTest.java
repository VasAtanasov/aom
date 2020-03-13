package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UpholsteryValidatorTest {
  private UpholsteryValidator validator = new UpholsteryValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidUpholstery() {
      assertThat(isValid("CLOTH")).isTrue();
      assertThat(isValid("FULL_LEATHER")).isTrue();
      assertThat(isValid("PART_LEATHER")).isTrue();
      assertThat(isValid("VELOUR")).isTrue();
      assertThat(isValid("OTHER")).isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidUpholstery() {
      assertThat(isValid("Not valid")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return validator.isValid(value, null);
  }
}
