package bg.autohouse.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public static final String EXCEPTION_MAKER_NOT_FOUND = "No maker was found with the provided id!";
  public static final String BAD_REQUEST = "Validation of one or more arguments failed!";

  public static final String REQUEST_SUCCESS_ = "Data was retrieved successful";
  public static final String MODEL_CREATE_SUCCESS = "Successfully add model %s for %s!";
}
