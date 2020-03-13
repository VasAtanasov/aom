package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FeatureValidatorTest {
  private FeatureValidator validator = new FeatureValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidFeature() {
      assertThat(isValid("AUTOMATIC_CLIMATE_CONTROL")).isTrue();
      assertThat(isValid("TRACTION_CONTROL")).isTrue();
      assertThat(isValid("ELECTRICALLY_ADJUSTABLE_SEATS")).isTrue();
      assertThat(isValid("MP3")).isTrue();
      assertThat(isValid("PANORAMA_ROOF")).isTrue();
      assertThat(isValid("AUXILIARY_HEATING")).isTrue();
      assertThat(isValid("SPORT_PACKAGE")).isTrue();
      assertThat(isValid("START_STOP_SYSTEM")).isTrue();
      assertThat(isValid("MULTI_FUNCTION_STEERING_WHEEL")).isTrue();
      assertThat(isValid("DAYTIME_RUNNING_LIGHTS")).isTrue();
      assertThat(isValid("SPORT_SUSPENSION")).isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidFeature() {
      assertThat(isValid("Not valid")).isFalse();
      assertThat(isValid("")).isFalse();
    }
  }

  private boolean isValid(String value) {
    return validator.isValid(value, null);
  }
}
