package bg.autohouse.errors;

import bg.autohouse.web.enums.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 6698735357552974864L;

  public AccountNotFoundException() {
    super(RestMessage.USER_ACCOUNT_NOT_FOUND.name());
  }
}
