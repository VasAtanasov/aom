package bg.autohouse.errors;

public class UserRegistrationDisabledException extends RuntimeException {
  private static final long serialVersionUID = -8947471734173343716L;

  public UserRegistrationDisabledException(String message) {
    super(message);
  }

  public UserRegistrationDisabledException() {
    super();
  }
}
