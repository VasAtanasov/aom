package bg.autohouse.web.models.offer;

import bg.autohouse.HibernateValidatorTest;
import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.data.models.enums.Color;
import bg.autohouse.data.models.enums.Drive;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.enums.State;
import bg.autohouse.data.models.enums.Transmission;
import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.web.models.request.offer.VehicleCreateRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
public class VehicleCreateRequestTest extends HibernateValidatorTest {

  private static final String ERROR_MESSAGE = "Positive value must be provided: %s";
  private static final String INVALID_VALUE = "Invalid value";

  private static final List<String> validFeatures =
      Arrays.asList(
          Feature.DRIVER_SIDE_AIRBAG.name(),
          Feature.MULTI_FUNCTION_STEERING_WHEEL.name(),
          Feature.NIGHT_VIEW_ASSIST.name(),
          Feature.EMERGENCY_BRAKE_ASSISTANT.name(),
          Feature.DRIVER_SIDE_AIRBAG.name());

  private static final List<String> invalidFeatures =
      Arrays.asList(
          Feature.DRIVER_SIDE_AIRBAG.name(),
          Feature.MULTI_FUNCTION_STEERING_WHEEL.name(),
          Feature.NIGHT_VIEW_ASSIST.name(),
          "invalid feature",
          Feature.DRIVER_SIDE_AIRBAG.name());

  @ParameterizedTest
  @MethodSource("createRequestSet")
  void whenCreateRequest_withInvalidData_shouldInvalidateWithMessage(
      VehicleCreateRequest vehicleCreateRequest, String field, String message) {

    isInvalid(vehicleCreateRequest);
    assertMessage(vehicleCreateRequest, field, message);
    log.info(field + ": " + message);
  }

  private static Stream<Arguments> createRequestSet() {
    return Stream.of(
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(-10)
                .doors(4)
                .state(State.NEW.name())
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(Transmission.AUTOMATIC.name())
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(Color.RED.name())
                .features(validFeatures)
                .build(),
            "mileage",
            String.format(ERROR_MESSAGE, "mileage")),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(10)
                .doors(-4)
                .state(State.NEW.name())
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(Transmission.AUTOMATIC.name())
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(Color.RED.name())
                .features(validFeatures)
                .build(),
            "doors",
            String.format(ERROR_MESSAGE, "doors")),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(10)
                .doors(4)
                .state(INVALID_VALUE)
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(Transmission.AUTOMATIC.name())
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(Color.RED.name())
                .features(validFeatures)
                .build(),
            "state",
            ValidationMessages.INVALID_STATE),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(10)
                .doors(4)
                .state(State.NEW.name())
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(INVALID_VALUE)
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(Color.RED.name())
                .features(validFeatures)
                .build(),
            "transmission",
            ValidationMessages.INVALID_TRANSMISSION),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(10)
                .doors(4)
                .state(State.NEW.name())
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(Transmission.AUTOMATIC.name())
                .drive(INVALID_VALUE)
                .color(Color.RED.name())
                .features(validFeatures)
                .build(),
            "drive",
            ValidationMessages.INVALID_DRIVE),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(10)
                .doors(4)
                .state(State.NEW.name())
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(Transmission.AUTOMATIC.name())
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(INVALID_VALUE)
                .features(validFeatures)
                .build(),
            "color",
            ValidationMessages.INVALID_COLOR),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(10)
                .doors(4)
                .state(State.NEW.name())
                .bodyStyle(INVALID_VALUE)
                .transmission(Transmission.AUTOMATIC.name())
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(Color.RED.name())
                .features(validFeatures)
                .build(),
            "bodyStyle",
            ValidationMessages.INVALID_BODY_STYLE),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType("Invalid Fuel")
                .mileage(10)
                .doors(4)
                .state(State.NEW.name())
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(Transmission.AUTOMATIC.name())
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(Color.RED.name())
                .features(validFeatures)
                .build(),
            "fuelType",
            ValidationMessages.INVALID_FUEL_TYPE_NULL),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(10)
                .doors(4)
                .state(State.NEW.name())
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(Transmission.AUTOMATIC.name())
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(Color.RED.name())
                .features(invalidFeatures)
                .build(),
            "features",
            ValidationMessages.INVALID_FEATURE),
        Arguments.of(
            VehicleCreateRequest.builder()
                .fuelType(FuelType.GASOLINE.name())
                .mileage(10)
                .doors(4)
                .state(State.NEW.name())
                .bodyStyle(BodyStyle.CONVERTIBLE.name())
                .transmission(Transmission.AUTOMATIC.name())
                .drive(Drive.ALL_WHEEL_DRIVE.name())
                .color(Color.RED.name())
                .features(new ArrayList<>())
                .build(),
            "features",
            ValidationMessages.INVALID_FEATURE));
  }
}
