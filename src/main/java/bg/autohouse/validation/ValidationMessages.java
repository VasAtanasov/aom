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
}
