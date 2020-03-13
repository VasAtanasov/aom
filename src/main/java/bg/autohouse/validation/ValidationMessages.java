package bg.autohouse.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessages {

  public static final String CODE_MAKER_NAME_BLANK = "maker.name.blank";
  public static final String MAKER_NAME_BLANK =
      "Maker name is required and cannot be blank string.";

  public static final String CODE_MODEL_NAME_BLANK = "model.name.blank";
  public static final String MODEL_NAME_BLANK =
      "Model name is required and cannot be blank string.";

  public static final String CODE_MAKER_NAME_LENGTH = "maker.name.length";
  public static final String MAKER_NAME_LENGTH =
      "Makers name length should be between {min} and {max} symbols.";

  public static final String CODE_MODEL_NAME_LENGTH = "model.name.length";
  public static final String MODEL_NAME_LENGTH =
      "Model name length should be between {min} and {max} symbols.";

  public static final String MAKER_NULL = "Model must be assigned to a given Maker";
  public static final String CODE_MAKER_NULL = "maker.null";

  public static final String CODE_MAKER_NAME_EXISTS = "maker.name.exists";
  public static final String MAKER_NAME_EXISTS = "Maker name already exists.";

  public static final String CODE_MAKER_ID_NULL = "maker.id.null";
  public static final String MAKER_ID_NULL = "Maker id cannot be null.";

  public static final String CODE_INVALID_FUEL_TYPE_NULL = "fuelType.invalid.value";
  public static final String INVALID_FUEL_TYPE_NULL =
      "Provided fuel type value is non existent or invalid.";

  public static final String CODE_INVALID_EURO_STANDARD_NULL = "euroStandard.invalid.value";
  public static final String INVALID_EURO_STANDARD_NULL =
      "Provided euro standard value is non existent or invalid.";

  public static final String CODE_INVALID_ENGINE_POWER = "engine.power.invalid";
  public static final String INVALID_ENGINE_POWER = "Engine power must valid be positive number";

  public static final String CODE_INVALID_STATE = "vehicle.state.invalid";
  public static final String INVALID_STATE = "Invalid vehicle sate";

  public static final String CODE_INVALID_BODY_STYLE = "vehicle.body.style.invalid";
  public static final String INVALID_BODY_STYLE = "Invalid or non existent body style";

  public static final String CODE_INVALID_TRANSMISSION = "vehicle.transmission.invalid";
  public static final String INVALID_TRANSMISSION = "Invalid or non existent transmission";

  public static final String CODE_INVALID_DRIVE = "vehicle.drive.invalid";
  public static final String INVALID_DRIVE = "Invalid or non existent drive-train";

  public static final String CODE_INVALID_COLOR = "vehicle.color.invalid";
  public static final String INVALID_COLOR = "Invalid or non existent color";

  public static final String CODE_INVALID_UPHOLSTERY = "vehicle.upholstery.invalid";
  public static final String INVALID_UPHOLSTERY = "Invalid or non existent upholstery";

  public static final String CODE_INVALID_FEATURE = "vehicle.feature.invalid";
  public static final String INVALID_FEATURE = "Invalid or non existent feature";

  public static final String CODE_INVALID_NUMBER = "number.invalid";
  public static final String INVALID_NUMBER = "Positive value must be provided: {field}";
}
