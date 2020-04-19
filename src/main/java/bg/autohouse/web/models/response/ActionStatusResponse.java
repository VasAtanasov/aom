package bg.autohouse.web.models.response;

import bg.autohouse.web.enums.ActionStatus;
import bg.autohouse.web.enums.ActionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActionStatusResponse {
  @JsonProperty("type")
  private ActionType type;

  @JsonProperty("status")
  private ActionStatus status;
}
