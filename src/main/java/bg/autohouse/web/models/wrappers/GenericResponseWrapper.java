package bg.autohouse.web.models.wrappers;

import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.enums.RestStatus;
import org.springframework.http.HttpStatus;

public class GenericResponseWrapper extends ResponseWrapperImpl {
  private final Object data;

  public GenericResponseWrapper(
      HttpStatus code, RestMessage message, RestStatus status, Object data) {
    super(code, message, status);
    this.data = data;
  }

  public Object getData() {
    return data;
  }

  @Override
  public String toString() {
    return "GenericResponseWrapper{"
        + "data="
        + data
        + "status='"
        + status
        + '\''
        + ", code="
        + code
        + ", message='"
        + message
        + '\''
        + '}';
  }
}
