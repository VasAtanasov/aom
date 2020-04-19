package bg.autohouse.errors;

import bg.autohouse.web.enums.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UsernamePasswordLoginFailedException extends RuntimeException {

  public UsernamePasswordLoginFailedException() {
    super(RestMessage.USER_LOGIN_FAILED.name());
  }

  public UsernamePasswordLoginFailedException(RestMessage restMessage) {
    super(restMessage.name());
  }

  private static final long serialVersionUID = -722942067420667784L;
}
