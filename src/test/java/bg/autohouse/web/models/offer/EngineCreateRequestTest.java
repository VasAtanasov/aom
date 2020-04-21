package bg.autohouse.web.models.offer;

import bg.autohouse.HibernateValidatorTest;
import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.web.models.request.offer.EngineCreateRequest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EngineCreateRequestTest extends HibernateValidatorTest {

  @ParameterizedTest
  @MethodSource("createRequestSet")
  void whenCreateRequest_withInvalidData_shouldInvalidateWithMessage(
      EngineCreateRequest createRequest, String field, String message) {

    isInvalid(createRequest);
    assertMessage(createRequest, field, message);
  }

  private static Stream<Arguments> createRequestSet() {
    return Stream.of(
        Arguments.of(
            EngineCreateRequest.of("fuelType", 100, "EURO6"),
            "fuelType",
            ValidationMessages.INVALID_FUEL_TYPE_NULL),
        Arguments.of(
            EngineCreateRequest.of("Gasoline", -100, "EURO6"),
            "power",
            ValidationMessages.INVALID_ENGINE_POWER),
        Arguments.of(
            EngineCreateRequest.of("Gasoline", 100, "EURO10"),
            "euroStandard",
            ValidationMessages.INVALID_EURO_STANDARD_NULL),
        Arguments.of(
            EngineCreateRequest.of(null, 100, "EURO6"),
            "fuelType",
            ValidationMessages.INVALID_FUEL_TYPE_NULL),
        Arguments.of(
            EngineCreateRequest.of("Gasoline", 100, null),
            "euroStandard",
            ValidationMessages.INVALID_EURO_STANDARD_NULL),
        Arguments.of(
            EngineCreateRequest.of("Gasoline", null, "EURO6"),
            "power",
            ValidationMessages.INVALID_ENGINE_POWER));
  }
}
