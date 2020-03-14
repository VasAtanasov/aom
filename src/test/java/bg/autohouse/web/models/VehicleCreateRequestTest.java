package bg.autohouse.web.models;


import bg.autohouse.HibernateValidatorTest;
import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.data.models.enums.Color;
import bg.autohouse.data.models.enums.Drive;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.enums.State;
import bg.autohouse.data.models.enums.Transmission;
import bg.autohouse.data.models.enums.Upholstery;
import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.web.models.request.EngineCreateRequest;
import bg.autohouse.web.models.request.VehicleCreateRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class VehicleCreateRequestTest extends HibernateValidatorTest {
  private static final String ERROR_MESSAGE = "Positive value must be provided: %s";
  private static final String INVALID_VALUE = "Invalid value";

  private static final EngineCreateRequest validEngine =
      EngineCreateRequest.of("Gasoline", 100, "EURO6");

  private static final EngineCreateRequest invalidEngineFuelType =
      EngineCreateRequest.of("Invalid Fuel", 100, "EURO6");

  private static final EngineCreateRequest invalidEnginePower =
      EngineCreateRequest.of("Gasoline", -100, "EURO6");

  private static final EngineCreateRequest invalidEngineEuroStandard =
      EngineCreateRequest.of("Gasoline", 100, "EURO100");

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
  }

  private static Stream<Arguments> createRequestSet() {
    return Stream.of(
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(-10)
                .seats(5)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .features(validFeatures)
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .build(),
            "mileage",
            String.format(ERROR_MESSAGE, "mileage")),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(-10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .features(validFeatures)
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .build(),
            "seats",
            String.format(ERROR_MESSAGE, "seats")),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(5)
                .doors(-4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "doors",
            String.format(ERROR_MESSAGE, "doors")),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(INVALID_VALUE)
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "state",
            ValidationMessages.INVALID_STATE),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(INVALID_VALUE)
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "transmission",
            ValidationMessages.INVALID_TRANSMISSION),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(INVALID_VALUE)
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "drive",
            ValidationMessages.INVALID_DRIVE),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(INVALID_VALUE)
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "color",
            ValidationMessages.INVALID_COLOR),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(INVALID_VALUE)
                .features(validFeatures)
                .build(),
            "upholstery",
            ValidationMessages.INVALID_UPHOLSTERY),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(INVALID_VALUE)
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "bodyStyle",
            ValidationMessages.INVALID_BODY_STYLE),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(invalidEngineFuelType)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "engine.fuelType",
            ValidationMessages.INVALID_FUEL_TYPE_NULL),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(invalidEnginePower)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "engine.power",
            ValidationMessages.INVALID_ENGINE_POWER),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(invalidEngineEuroStandard)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(validFeatures)
                .build(),
            "engine.euroStandard",
            ValidationMessages.INVALID_EURO_STANDARD_NULL),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(invalidFeatures)
                .build(),
            "features",
            ValidationMessages.INVALID_FEATURE),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(validEngine)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(BodyStyle.CONVERTIBLE.toString())
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .features(new ArrayList<>())
                .build(),
            "features",
            ValidationMessages.INVALID_FEATURE));
  }
}
