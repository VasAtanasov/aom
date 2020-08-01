package bg.autohouse.errors;

import bg.autohouse.web.enums.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOfferException extends RuntimeException {

  private static final long serialVersionUID = -1027866669613097253L;

  public InvalidOfferException() {
    super(RestMessage.INVALID_OFFER_DATA.name());
  }

  public InvalidOfferException(final String message) {
    super(message);
  }
}
