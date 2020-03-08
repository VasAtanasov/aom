package bg.autohouse.web.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EngineCreateRequest {
  private String fuelType;
  private Integer power;
  private String euroStandard;
}
