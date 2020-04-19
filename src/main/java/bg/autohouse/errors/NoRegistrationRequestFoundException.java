package bg.autohouse.errors;

import bg.autohouse.web.enums.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoRegistrationRequestFoundException extends RuntimeException {

  private static final long serialVersionUID = 7015589285504091360L;

  public NoRegistrationRequestFoundException() {
    super(RestMessage.USER_REGISTRATION_REQUEST_NOT_FOUND.name());
  }
}
