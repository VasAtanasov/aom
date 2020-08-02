package bg.autohouse.web.enums;

public enum RestMessage {
  REQUEST_SUCCESS,

  MAKERS_GET_SUCCESSFUL,
  MAKER_GET_SUCCESSFUL,
  MAKER_MODEL_CREATED,
  MAKER_CREATED,

  INITIAL_STATE_GET_SUCCESSFUL,

  REGISTRATION_VERIFICATION_TOKEN_SENT,
  PASSWORD_RESET_VERIFICATION_TOKEN_SENT,
  PASSWORD_RESET_SUCCESSFUL,
  USER_REGISTRATION_VERIFIED,
  USER_ALREADY_EXISTS,
  INVALID_REGISTRATION_TOKEN,
  INVALID_TOKEN,
  INVALID_USERNAME,
  LOGOUT_SUCCESSFUL,
  LOGOUT_FAILED,
  USER_LOGIN_SUCCESSFUL,
  USER_LOGIN_FAILED,

  USER_ALREADY_HAS_ACCOUNT,
  PRIVATE_SELLER_ACCOUNT_CREATED,
  DEALER_ACCOUNT_REQUEST_CREATED,
  INVALID_CREDENTIALS,
  OTP_VALID,
  OTP_INVALID,
  TOKEN_STILL_VALID,
  TOKEN_EXPIRED,
  INVALID_PASSWORD,
  INVALID_ACCOUNT_TYPE,

  IMAGE_UPLOAD_SUCCESSFUL,
  ERROR_WHILE_FETCHING_IMAGE,
  INVALID_MEDIA_TYPE,
  USER_REGISTRATION_DISABLED,
  USER_ACCOUNT_DISABLED,
  PERMISSION_DENIED,
  INVALID_REQUEST,
  INVALID_UPDATE_OPERATION,
  CONSTRAINT_VIOLATION,
  INTERNAL_SERVER_ERROR,
  PARAMETER_VALIDATION_FAILURE,
  SOMETHING_WENT_WRONG,
  BAD_CREDENTIALS,
  USER_ACCOUNT_LOCKED,
  USER_ACCOUNT_EXPIRED,
  USER_CREDENTIALS_EXPIRED,
  USER_NOT_FOUND,
  MAKER_NOT_FOUND,
  USER_REGISTRATION_REQUEST_NOT_FOUND,
  USER_USERNAME_REQUIRED,
  MODEL_ALREADY_EXISTS,
  MAKER_ALREADY_EXISTS,
  USER_ACCOUNT_NOT_FOUND,
  INVALID_ACCOUNT_DATA,
  REQUIRED_FIELD_MISSING,
  LOCATION_NOT_FOUND,
  MODEL_GET_SUCCESSFUL,
  INVALID_COLOR,
  INVALID_BODY_STYLE,
  INVALID_DRIVE,
  INVALID_FUEL_TYPE,
  INVALID_VEHICLE_STATE,
  INVALID_TRANSMISSION,
  INVALID_FEATURE,
  MEDIA_NOT_FOUND,
  OFFER_ID_NOT_FOUND,
  OFFER_ADD_TO_FAVORITES,
  OFFER_REMOVED_FROM_FAVORITES,
  BLANK_USER_ID,
  BLANK_CURRENT_ROLE,
  BLANK_NEW_ROLE,
  ROLE_CHANGE_NOT_ALLOWED,
  CURRENT_USER_IS_DISABLED,
  OFFER_CREATED_SUCCESS,
  INVALID_OFFER_DATA,
  MISSING_IMAGE,
  HAS_NO_IMAGES,
  INVALID_OFFER_PRICE,
  INVALID_OFFER_DESCRIPTION,
  INVALID_OFFER_PHONE_NUMBER,
  ACCOUNT_MISSING,
  LOCATION_MISSING,
  VEHICLE_MISSING,
  INVALID_VEHICLE_YEAR,
  INVALID_VEHICLE_MILEAGE,
  INVALID_VEHICLE_DOORS,
  MAX_OFFER_COUNT_REACHED;
}
