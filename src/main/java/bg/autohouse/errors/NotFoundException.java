package bg.autohouse.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 7015589285504091360L;

  public NotFoundException() {}

  public NotFoundException(String s) {
    super(s);
  }

  public NotFoundException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public NotFoundException(Throwable throwable) {
    super(throwable);
  }
}
