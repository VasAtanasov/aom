package bg.autohouse.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseException extends RuntimeException {

  private static final long serialVersionUID = 3859964215866372293L;
  private int status;

  // TODO lombok call to super
  BaseException(String message, int status) {
    super(message);
    this.setStatus(status);
  }
}
