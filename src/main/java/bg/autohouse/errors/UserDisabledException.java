package bg.autohouse.errors;

import bg.autohouse.web.enums.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserDisabledException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UserDisabledException() {
    super(RestMessage.CURRENT_USER_IS_DISABLED.name());
  }
}
