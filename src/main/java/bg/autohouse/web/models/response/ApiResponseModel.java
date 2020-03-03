package bg.autohouse.web.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel("Api response wrapper.")
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"success", "message", "status", "timestamp", "payload"})
public class ApiResponseModel {
  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonProperty("timestamp")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();

  @JsonProperty("status")
  private String status;

  @JsonProperty("payload")
  @Builder.Default
  private Object payload = new HashMap(1);
}
