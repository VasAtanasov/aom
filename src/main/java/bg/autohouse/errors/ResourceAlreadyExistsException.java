package bg.autohouse.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {
  private static final long serialVersionUID = -7754651575929506002L;

  public ResourceAlreadyExistsException(String message) {
    super(message);
  }
}
