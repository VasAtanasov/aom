package bg.autohouse.errors;

import bg.autohouse.common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ModelNotFoundException extends BaseException {
  private static final long serialVersionUID = -7754651575929506002L;

  public ModelNotFoundException() {
    this(Constants.EXCEPTION_MODEL_NOT_FOUND);
  }

  public ModelNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
