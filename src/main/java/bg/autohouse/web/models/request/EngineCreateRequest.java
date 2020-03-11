package bg.autohouse.web.models.request;

import bg.autohouse.validation.offer.ValidEuroStandard;
import bg.autohouse.validation.offer.ValidFuelType;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EngineCreateRequest {

  @ValidFuelType private String fuelType;

  @Min(0)
  private Integer power;

  @ValidEuroStandard private String euroStandard;
}
