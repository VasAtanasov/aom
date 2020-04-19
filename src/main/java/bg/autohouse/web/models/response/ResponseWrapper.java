package bg.autohouse.web.models.response;

import bg.autohouse.web.enums.RestMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"success", "message", "status", "data", "errors", "action"})
public class ResponseWrapper {
  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private RestMessage message;

  @JsonProperty("status")
  private Integer status;

  @JsonProperty("data")
  private Object data;

  @JsonProperty("errors")
  private Object errors;

  @JsonProperty("action")
  private ActionStatusResponse action;
}
