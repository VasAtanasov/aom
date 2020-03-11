package bg.autohouse.web.models.request;

import javax.validation.Valid;
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
public class VehicleCreateRequest {
  // @Positive @NotNull private Long makerId;
  // @Positive @NotNull private Long modelId;

  @Valid private EngineCreateRequest engine;

  // @Positive @NotNull private Integer mileage;
  // @Positive @NotNull private Integer seats;
  // @Positive @NotNull private Integer doors;
  // @Positive @NotNull private String state;
}
