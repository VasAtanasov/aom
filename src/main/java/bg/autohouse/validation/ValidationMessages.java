package bg.autohouse.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessages {

  public static final String MAKER_NAME_BLANK =
      "Maker name is required and cannot be blank string.";

  public static final String MODEL_NAME_BLANK =
      "Model name is required and cannot be blank string.";

  public static final String MAKER_NAME_LENGTH =
      "Makers name length should be between {min} and {max} symbols.";

  public static final String MODEL_NAME_LENGTH =
      "Model name length should be between {min} and {max} symbols.";

  public static final String MAKER_NULL = "Model must be assigned to a given Maker";

  public static final String MAKER_NAME_EXISTS = "Maker name already exists.";

  public static final String MAKER_ID_NULL = "Maker id cannot be null.";

  public static final String INVALID_FUEL_TYPE_NULL =
      "Provided fuel type value is non existent or invalid.";

  public static final String INVALID_EURO_STANDARD_NULL =
      "Provided euro standard value is non existent or invalid.";

  public static final String INVALID_ENGINE_POWER = "Engine power must valid be positive number";

  public static final String INVALID_STATE = "Invalid vehicle sate";

  public static final String INVALID_BODY_STYLE = "Invalid or non existent body style";

  public static final String INVALID_TRANSMISSION = "Invalid or non existent transmission";

  public static final String INVALID_DRIVE = "Invalid or non existent drive-train";

  public static final String INVALID_COLOR = "Invalid or non existent color";

  public static final String INVALID_UPHOLSTERY = "Invalid or non existent upholstery";

  public static final String INVALID_FEATURE =
      "The list of features contains invalid or non existent feature";

  public static final String INVALID_NUMBER = "Positive value must be provided: {field}";

  public static final String INVALID_MODEL_ID =
      "Valid and existing maker and model id must be provided.";

  public static final String INVALID_PRICE = "Invalid offer price.";

  public static final String NULL_VALUE_OFFER_PRICE = "Missing value for offer price.";

  public static final String MISSING_VEHICLE_DATA = "Missing vehicle data.";

  public static final String MISSING_INVALID_DESCRIPTION = "Missing or invalid offer description.";
}
