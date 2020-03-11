package bg.autohouse.web.models;

import bg.autohouse.HibernateValidatorTest;
import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.web.models.request.EngineCreateRequest;
import bg.autohouse.web.models.request.VehicleCreateRequest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class VehicleCreateRequestTest extends HibernateValidatorTest {

  @ParameterizedTest
  @MethodSource("createRequestSet")
  void whenCreateRequest_withInvalidData_shouldInvalidateWithMessage(
      EngineCreateRequest engineCreateRequest, String field, String message) {

    VehicleCreateRequest vehicleCreateRequest =
        VehicleCreateRequest.builder().engine(engineCreateRequest).build();

    isInvalid(vehicleCreateRequest);
    assertMessage(vehicleCreateRequest, field, message);
  }

  private static Stream<Arguments> createRequestSet() {
    return Stream.of(
        Arguments.of(
            EngineCreateRequest.of("fuelType", 100, "EURO6"),
            "engine.fuelType",
            ValidationMessages.INVALID_FUEL_TYPE_NULL),
        Arguments.of(
            EngineCreateRequest.of("Gasoline", -100, "EURO6"),
            "engine.power",
            ValidationMessages.INVALID_ENGINE_POWER),
        Arguments.of(
            EngineCreateRequest.of("Gasoline", 100, "EURO10"),
            "engine.euroStandard",
            ValidationMessages.INVALID_EURO_STANDARD_NULL));
  }
}
