package bg.autohouse.validation;

import bg.autohouse.validation.offer.BodyStyleValidator;
import bg.autohouse.validation.offer.ColorValidator;
import bg.autohouse.validation.offer.DriveValidator;
import bg.autohouse.validation.offer.FeatureListValidator;
import bg.autohouse.validation.offer.FuelTypeValidator;
import bg.autohouse.validation.offer.StateValidator;
import bg.autohouse.validation.offer.TransmissionValidator;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validators {
  public static boolean isValidBodyStyle(final String value) {
    return BodyStyleValidator.validate(value);
  }

  public static boolean isValidColor(final String value) {
    return ColorValidator.validate(value);
  }

  public static boolean isValidDrive(final String value) {
    return DriveValidator.validate(value);
  }

  public static boolean isValidFeatureList(final List<String> values) {
    return FeatureListValidator.validate(values);
  }

  public static boolean isValidFuelType(final String value) {
    return FuelTypeValidator.validate(value);
  }

  public static boolean isValidState(final String value) {
    return StateValidator.validate(value);
  }

  public static boolean isValidTransmission(final String value) {
    return TransmissionValidator.validate(value);
  }
}
