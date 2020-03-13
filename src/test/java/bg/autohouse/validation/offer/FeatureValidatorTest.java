package bg.autohouse.validation.offer;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.enums.Feature;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FeatureValidatorTest {
  private FeatureListValidator validator = new FeatureListValidator();

  @Nested
  class ValidFlow {
    @Test
    void isValid_shouldReturnTrue_whenValidFeature() {
      assertThat(
              isValid(
                  Arrays.asList(
                      Feature.DRIVER_SIDE_AIRBAG.name(),
                      Feature.MULTI_FUNCTION_STEERING_WHEEL.name(),
                      Feature.NIGHT_VIEW_ASSIST.name(),
                      Feature.EMERGENCY_BRAKE_ASSISTANT.name(),
                      Feature.DRIVER_SIDE_AIRBAG.name())))
          .isTrue();
    }
  }

  @Nested
  class InvalidFlow {
    @Test
    void isValid_shouldReturnFalse_whenInvalidFeature() {
      assertThat(
              isValid(
                  Arrays.asList(
                      Feature.DRIVER_SIDE_AIRBAG.name(),
                      Feature.MULTI_FUNCTION_STEERING_WHEEL.name(),
                      Feature.NIGHT_VIEW_ASSIST.name(),
                      "invalid feature",
                      Feature.DRIVER_SIDE_AIRBAG.name())))
          .isFalse();
    }
  }

  private boolean isValid(List<String> value) {
    return validator.isValid(value, null);
  }
}
