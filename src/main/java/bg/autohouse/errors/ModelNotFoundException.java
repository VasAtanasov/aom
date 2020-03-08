package bg.autohouse.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ModelNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -7754651575929506002L;

  public ModelNotFoundException() {
    this(ExceptionsMessages.EXCEPTION_MODEL_NOT_FOUND);
  }

  public ModelNotFoundException(String message) {
    super(message);
  }
}
