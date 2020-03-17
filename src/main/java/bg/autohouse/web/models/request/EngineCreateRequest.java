package bg.autohouse.web.models.request;

import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.validation.offer.ValidEuroStandard;
import bg.autohouse.validation.offer.ValidFuelType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class EngineCreateRequest {

  @ValidFuelType private String fuelType;

  @NotNull(message = ValidationMessages.INVALID_ENGINE_POWER)
  @Positive(message = ValidationMessages.INVALID_ENGINE_POWER)
  @Builder.Default
  private Integer power = 1;

  @ValidEuroStandard private String euroStandard;
}