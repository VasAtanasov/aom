package bg.autohouse.errors;

import javax.validation.ValidationException;

public class MessageExposableValidationException extends ValidationException {

  private static final long serialVersionUID = -9111361300333957595L;

  public MessageExposableValidationException(final String errorMessage) {
    super(errorMessage);
  }
}
