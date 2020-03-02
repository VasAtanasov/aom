package bg.autohouse.web.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"success", "message", "status", "timestamp", "payload"})
public class ApiResponseModel {
  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonProperty("timestamp")
  private LocalDateTime timestamp;

  @JsonProperty("status")
  private String status;

  @JsonProperty("payload")
  @Builder.Default
  private Object payload = new HashMap(1);
}
