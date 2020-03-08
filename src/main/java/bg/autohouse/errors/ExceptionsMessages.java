package bg.autohouse.errors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionsMessages {
  public static final String EXCEPTION_MAKER_NOT_FOUND = "No maker was found with the provided id!";
  public static final String EXCEPTION_MODEL_NOT_FOUND = "No model was found with the provided id!";
  public static final String BAD_REQUEST = "Validation of one or more arguments failed!";
}
