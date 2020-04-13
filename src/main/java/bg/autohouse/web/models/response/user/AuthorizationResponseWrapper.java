package bg.autohouse.web.models.response.user;

import bg.autohouse.web.enums.RestMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationResponseWrapper {

  private RestMessage errorCode = null;
  private AuthorizedUserResponseModel user;

  @JsonCreator
  public AuthorizationResponseWrapper(@JsonProperty("user") AuthorizedUserResponseModel user) {
    this.user = user;
  }

  @JsonCreator
  public AuthorizationResponseWrapper(@JsonProperty("errorCode") RestMessage errorCode) {
    this.errorCode = errorCode;
  }
}
