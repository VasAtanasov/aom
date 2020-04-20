package bg.autohouse.errors;

import bg.autohouse.web.enums.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchUserException extends NullPointerException {

  private static final long serialVersionUID = -8947471734173343716L;

  public NoSuchUserException(String message) {
    super(message);
  }

  public NoSuchUserException() {
    super(RestMessage.USER_NOT_FOUND.name());
  }
}
