package bg.autohouse.errors;

import bg.autohouse.common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MakerNotFoundException extends BaseException {
  private static final long serialVersionUID = -7754651575929506002L;

  public MakerNotFoundException() {
    super(Constants.EXCEPTION_MAKER_NOT_FOUND, HttpStatus.NOT_FOUND.value());
  }

  public MakerNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND.value());
  }
}
