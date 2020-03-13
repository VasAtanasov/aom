package bg.autohouse.web.models;

import bg.autohouse.HibernateValidatorTest;
import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.data.models.enums.Color;
import bg.autohouse.data.models.enums.Drive;
import bg.autohouse.data.models.enums.State;
import bg.autohouse.data.models.enums.Transmission;
import bg.autohouse.data.models.enums.Upholstery;
import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.web.models.request.EngineCreateRequest;
import bg.autohouse.web.models.request.VehicleCreateRequest;
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
      EngineCreateRequest.of("Invalid Fuel", -100, "EURO6");

  private static final EngineCreateRequest invalidEngineEuroStandard =
      EngineCreateRequest.of("Invalid Fuel", 100, "EURO100");

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
                .bodyStyle(INVALID_VALUE)
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
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
                .bodyStyle(INVALID_VALUE)
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .build(),
            "engine.fuelType",
            ValidationMessages.INVALID_FUEL_TYPE_NULL),
        Arguments.of(
            VehicleCreateRequest.builder()
                .makerId(1L)
                .modelId(1L)
                .engine(invalidEngineEuroStandard)
                .mileage(10)
                .seats(10)
                .doors(4)
                .state(State.NEW.toString())
                .bodyStyle(INVALID_VALUE)
                .transmission(Transmission.AUTOMATIC.toString())
                .drive(Drive.ALL_WHEEL_DRIVE.toString())
                .color(Color.DARK_RED.toString())
                .upholstery(Upholstery.FULL_LEATHER.toString())
                .build(),
            "engine.fuelType",
            ValidationMessages.INVALID_FUEL_TYPE_NULL));
  }
}
