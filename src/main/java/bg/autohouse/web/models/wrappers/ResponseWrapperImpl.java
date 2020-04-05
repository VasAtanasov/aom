package bg.autohouse.web.models.wrappers;

import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.enums.RestStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapperImpl implements ResponseWrapper {

  protected final String status;
  protected final int code;
  protected final String message;

  public ResponseWrapperImpl(HttpStatus code, RestMessage message, RestStatus status) {
    this.code = code.value();
    this.message = String.valueOf(message);
    this.status = String.valueOf(status);
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String getStatus() {
    return status;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "ResponseWrapperImpl{"
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
