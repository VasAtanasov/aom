package bg.autohouse.errors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionsMessages {
  public static final String EXCEPTION_MAKER_NOT_FOUND = "No maker was found with the provided id!";
  public static final String EXCEPTION_MODEL_NOT_FOUND = "No model was found with the provided id!";
  public static final String BAD_REQUEST = "Validation of one or more arguments failed!";
  public static final String MAKER_WITH_NAME_EXISTS = "Maker with given name already exists!";
  public static final String MODEL_WITH_NAME_EXISTS = "Model with given name already exists!";
  public static final String INVALID_DATA_TYPE = "Type miss-match in request body.";

  public static final String USER_ALREADY_EXISTS = "User already exists!";
  public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
  public static final String EMAIL_ALREADY_EXISTS = "Email %s already exists";
  public static final String EXCEPTION_USER_NOT_FOUND_ID = "No user was found with provided id!";
  public static final String INVALID_TOKEN = "Invalid or nonexistent token!";
  public static final String INVALID_USER_LOGIN = "Invalid empty login username";
  public static final String NO_SUCH_USERNAME = "Could not lookup user, no such username.";
  public static final String NO_RECORD_FOUND = "Record with provided id is not found";
  public static final String INVALID_UPDATE_OPERATION = "Invalid update operation";
  public static final String DEALERSHIP_ALREADY_EXISTS =
      "Dealership by the name of %s already exists!";
  public static final String INVALID_DEALER_DATA = "Missing or invalid dealer data.";
  public static final String INVALID_LOCATION = "Invalid location identifier";
}
