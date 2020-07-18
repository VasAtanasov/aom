package bg.autohouse.errors;

import bg.autohouse.web.enums.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OfferNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -6659160931311043882L;

  public OfferNotFoundException() {
    super(RestMessage.OFFER_ID_NOT_FOUND.name());
  }
}
