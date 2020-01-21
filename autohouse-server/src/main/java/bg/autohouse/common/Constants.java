package bg.autohouse.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String EXCEPTION_NOT_FOUND = "Not found!";
    public static final String GENERAL_SERVICE_ERROR = "Something Went Wrong!";
    public static final String UNAUTHORIZED = "You are not authorized to do that";

    //Username exception message
    public static final String EXCEPTION_USERNAME_NOT_FOUND = "Username not found!";
    public static final String EXCEPTION_USERNAME_NOT_NULL = "Username cannot be null!";
    public static final String EXCEPTION_USERNAME_NOT_EMPTY = "Username cannot be empty!";
    public static final String EXCEPTION_USERNAME_LENGTH = "Username must be at least 3 symbols!";

    //Password exception message
    public static final String EXCEPTION_PASSWORD_NOT_NULL = "Password cannot be null!";
    public static final String EXCEPTION_PASSWORD_NOT_EMPTY = "Password cannot be empty!";
    public static final String EXCEPTION_PASSWORD_LENGTH = "Password must be between 4 and 20 symbols!";

    //Confirm password exception message
    public static final String EXCEPTION_CONFIRM_PASSWORD_NOT_NULL = "Confirm password cannot be null!";
    public static final String EXCEPTION_CONFIRM_PASSWORD_NOT_EMPTY = "Confirm password cannot be empty!";
    public static final String EXCEPTION_CONFIRM_PASSWORD_LENGTH = "Confirm password must be between 4 and 20 symbols!";

    //Email exception message
    public static final String EMAIL_PATTERN_STRING = "^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)";
    public static final String EXCEPTION_EMAIL_NOT_VALID = "Invalid email.";
    public static final String EXCEPTION_EMAIL_NOT_FOUND = "Email not found!";
    public static final String EXCEPTION_EMAIL_NOT_NULL = "Email cannot be null!";
    public static final String EXCEPTION_EMAIL_NOT_EMPTY = "Email cannot be empty!";

    // First Name exception message
    public static final String NAME_PATTERN = "^[A-Z][a-zA-Z]+";
    public static final String EXCEPTION_FIRST_NAME_LENGTH = "First name must be at least 3 symbols long.";
    public static final String EXCEPTION_FIRST_NAME_CAPITAL_LETTER = "First name must start with capital letter.";
    public static final String EXCEPTION_FIRST_NAME_NOT_NULL = "First name cannot be null.";
    public static final String EXCEPTION_FIRST_NAME_NOT_EMPTY = "First name cannot be empty.";

    // Last Name exception message
    public static final String EXCEPTION_LAST_NAME_LENGTH = "Last name must be at least 3 symbols long.";
    public static final String EXCEPTION_LAST_NAME_CAPITAL_LETTER = "Last name must start with capital letter.";
    public static final String EXCEPTION_LAST_NAME_NOT_NULL = "Last name cannot be null.";
    public static final String EXCEPTION_LAST_NAME_NOT_EMPTY = "Last name cannot be empty.";

    // Phone number exception message
    public static final String EXCEPTION_PHONE_NUMBER_PATTERN = "^\\+3598\\d{8}$";
    public static final String EXCEPTION_PHONE_NUMBER_NOT_VALID = "Invalid phone number.";
    public static final String EXCEPTION_PHONE_NUMBER_NOT_NULL = "Phone number cannot be null.";
    public static final String EXCEPTION_PHONE_NUMBER_NOT_EMPTY = "Phone number cannot be empty.";

    // Error massages
    public static final String EXCEPTION_USERNAME_EXISTS = "User with such username already exist!";
    public static final String EXCEPTION_EMAIL_EXISTS = "User with such email already exist!";
    public static final String EXCEPTION_USERNAME_OR_EMAIL_NOT_FOUND = "User with such username or email doesn't exist";
    public static final String EXCEPTION_USERNAME_OR_EMAIL_EXISTS = "User with such username or email already exist";

    // Offer exception message
    public static final String EXCEPTION_MAKER_NOT_NUlL = "Maker must be selected!";
    public static final String EXCEPTION_MAKER_NOT_EMPTY = "Maker must be selected!!";

    public static final String EXCEPTION_MODEL_NOT_NUlL = "Model must be selected!";
    public static final String EXCEPTION_MODEL_NOT_EMPTY = "Model must be selected!";

    public static final String EXCEPTION_PRICE_NOT_NUlL = "Price must be selected!";
    public static final String EXCEPTION_PRICE_NOT_EMPTY = "Price must be selected!";

    public static final String EXCEPTION_STATE_NOT_EMPTY_NOT_NULL = "Condition must be selected!";
    public static final String EXCEPTION_BODY_STYLE_NOT_EMPTY_NOT_NULL = "Body style must be selected!";
    public static final String EXCEPTION_LOCATION_NOT_EMPTY_NOT_NULL = "Location must be selected!";
    public static final String EXCEPTION_FUEL_TYPE_NOT_EMPTY_NOT_NULL = "Fuel type must be selected!";
    public static final String EXCEPTION_POWER_TYPE_NOT_EMPTY_NOT_NULL = "Power type must be selected!";
    public static final String EXCEPTION_POWER_NOT_EMPTY_NOT_NULL = "Valid power output must be inserted!";


    public static final String EXCEPTION_USER_NOT_FOUND = "User with provided username was not found!";
    public static final String EXCEPTION_MAKER_NOT_FOUND = "Maker with provided id was not found!";
    public static final String EXCEPTION_MODEL_NOT_FOUND = "Model with provided id was not found!";
    public static final String EXCEPTION_LOCATION_NOT_FOUND = "Location with provided id was not found!";
    public static final String EXCEPTION_COLOR_NOT_FOUND = "Color with provided id was not found!";

}
