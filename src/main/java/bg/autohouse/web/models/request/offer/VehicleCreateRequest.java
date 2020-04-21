package bg.autohouse.web.models.request.offer;

import bg.autohouse.validation.offer.ValidBodyStyle;
import bg.autohouse.validation.offer.ValidColor;
import bg.autohouse.validation.offer.ValidDrive;
import bg.autohouse.validation.offer.ValidFeatureList;
import bg.autohouse.validation.offer.ValidNumber;
import bg.autohouse.validation.offer.ValidState;
import bg.autohouse.validation.offer.ValidTransmission;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
  @NotNull private Long makerId;
  @NotNull private Long modelId;

  @Valid private EngineCreateRequest engine;

  @ValidNumber(field = "mileage")
  private Integer mileage;

  @ValidNumber(field = "seats")
  private Integer seats;

  @ValidNumber(field = "doors")
  private Integer doors;

  @ValidState private String state;
  @ValidBodyStyle private String bodyStyle;
  @ValidTransmission private String transmission;
  @ValidDrive private String drive;
  @ValidColor private String color;

  @ValidFeatureList @Builder.Default private List<String> features = new ArrayList<>();

  @Builder.Default private boolean hasAccident = false;
}
