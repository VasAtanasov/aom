package bg.autohouse.web.models.offer;

import bg.autohouse.HibernateValidatorTest;
import bg.autohouse.data.models.enums.*;
import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import bg.autohouse.web.models.request.offer.VehicleCreateRequest;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OfferCreateRequestTest extends HibernateValidatorTest {

  private static final String INVALID_VALUE = "Invalid value";

  private static final VehicleCreateRequest VALID_VEHICLE =
      VehicleCreateRequest.builder()
          .fuelType(FuelType.GASOLINE.name())
          .mileage(10)
          .doors(4)
          .state(State.NEW.name())
          .bodyStyle(BodyStyle.CONVERTIBLE.name())
          .transmission(Transmission.AUTOMATIC.name())
          .drive(Drive.ALL_WHEEL_DRIVE.name())
          .color(Color.RED.name())
          .features(
              Arrays.asList(
                  Feature.DRIVER_SIDE_AIRBAG.name(),
                  Feature.MULTI_FUNCTION_STEERING_WHEEL.name(),
                  Feature.NIGHT_VIEW_ASSIST.name(),
                  Feature.EMERGENCY_BRAKE_ASSISTANT.name(),
                  Feature.DRIVER_SIDE_AIRBAG.name()))
          .build();

  private static final VehicleCreateRequest INVALID_VEHICLE_STATE =
      VehicleCreateRequest.builder()
          .fuelType(FuelType.GASOLINE.name())
          .mileage(10)
          .doors(4)
          .state(INVALID_VALUE)
          .bodyStyle(BodyStyle.CONVERTIBLE.name())
          .transmission(Transmission.AUTOMATIC.name())
          .drive(Drive.ALL_WHEEL_DRIVE.name())
          .color(Color.RED.name())
          .features(
              Arrays.asList(
                  Feature.DRIVER_SIDE_AIRBAG.name(),
                  Feature.MULTI_FUNCTION_STEERING_WHEEL.name(),
                  Feature.NIGHT_VIEW_ASSIST.name(),
                  Feature.EMERGENCY_BRAKE_ASSISTANT.name(),
                  Feature.DRIVER_SIDE_AIRBAG.name()))
          .build();

  private static final VehicleCreateRequest INVALID_VEHICLE_ENGINE_FUEL_TYPE =
      VehicleCreateRequest.builder()
          .fuelType(INVALID_VALUE)
          .mileage(10)
          .doors(4)
          .state(State.NEW.name())
          .bodyStyle(BodyStyle.CONVERTIBLE.name())
          .transmission(Transmission.AUTOMATIC.name())
          .drive(Drive.ALL_WHEEL_DRIVE.name())
          .color(Color.RED.name())
          .features(
              Arrays.asList(
                  Feature.DRIVER_SIDE_AIRBAG.name(),
                  Feature.MULTI_FUNCTION_STEERING_WHEEL.name(),
                  Feature.NIGHT_VIEW_ASSIST.name(),
                  Feature.EMERGENCY_BRAKE_ASSISTANT.name(),
                  Feature.DRIVER_SIDE_AIRBAG.name()))
          .build();

  private static final String VALID_DESCRIPTION = "This is valid description.";
  private static final Integer VALID_PRICE = 1023213;

  @ParameterizedTest
  @MethodSource("createRequestSet")
  void whenCreateRequest_withInvalidData_shouldInvalidateWithMessage(
      OfferCreateRequest offerCreateRequest, String field, String message) {

    isInvalid(offerCreateRequest);
    assertMessage(offerCreateRequest, field, message);
  }

  private static Stream<Arguments> createRequestSet() {
    return Stream.of(
        Arguments.of(
            OfferCreateRequest.builder()
                .vehicle(VALID_VEHICLE)
                .price(-123122)
                .description(VALID_DESCRIPTION)
                .build(),
            "price",
            ValidationMessages.INVALID_PRICE),
        Arguments.of(
            OfferCreateRequest.builder()
                .vehicle(VALID_VEHICLE)
                .price(null)
                .description(VALID_DESCRIPTION)
                .build(),
            "price",
            ValidationMessages.NULL_VALUE_OFFER_PRICE),
        Arguments.of(
            OfferCreateRequest.builder()
                .vehicle(null)
                .price(VALID_PRICE)
                .description(VALID_DESCRIPTION)
                .build(),
            "vehicle",
            ValidationMessages.MISSING_VEHICLE_DATA),
        Arguments.of(
            OfferCreateRequest.builder()
                .vehicle(VALID_VEHICLE)
                .price(VALID_PRICE)
                .description(null)
                .build(),
            "description",
            ValidationMessages.MISSING_INVALID_DESCRIPTION),
        Arguments.of(
            OfferCreateRequest.builder()
                .vehicle(VALID_VEHICLE)
                .price(VALID_PRICE)
                .description("")
                .build(),
            "description",
            ValidationMessages.MISSING_INVALID_DESCRIPTION),
        Arguments.of(
            OfferCreateRequest.builder()
                .vehicle(INVALID_VEHICLE_STATE)
                .price(VALID_PRICE)
                .description(VALID_DESCRIPTION)
                .build(),
            "vehicle.state",
            ValidationMessages.INVALID_STATE),
        Arguments.of(
            OfferCreateRequest.builder()
                .vehicle(INVALID_VEHICLE_ENGINE_FUEL_TYPE)
                .price(VALID_PRICE)
                .description(VALID_DESCRIPTION)
                .build(),
            "vehicle.fuelType",
            ValidationMessages.INVALID_FUEL_TYPE_NULL));
  }
}
