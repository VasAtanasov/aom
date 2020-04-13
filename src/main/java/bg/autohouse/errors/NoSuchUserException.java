package bg.autohouse.errors;

public class NoSuchUserException extends NullPointerException {

  private static final long serialVersionUID = -8947471734173343716L;

  public NoSuchUserException(String message) {
    super(message);
  }
}
