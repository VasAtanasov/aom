package bg.autohouse.errors;

import bg.autohouse.web.enums.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleChangeException extends RuntimeException {

  private static final long serialVersionUID = -6396835863877917579L;

  public RoleChangeException() {
    super(RestMessage.ROLE_CHANGE_NOT_ALLOWED.name());
  }

  public RoleChangeException(String message) {
    super(message);
  }
}
