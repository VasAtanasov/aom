package bg.autohouse.web.models.request;

import bg.autohouse.validation.offer.ValidBodyStyle;
import bg.autohouse.validation.offer.ValidColor;
import bg.autohouse.validation.offer.ValidDrive;
import bg.autohouse.validation.offer.ValidNumber;
import bg.autohouse.validation.offer.ValidState;
import bg.autohouse.validation.offer.ValidTransmission;
import bg.autohouse.validation.offer.ValidUpholstery;
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
  // TODO Existent validation
  // TODO Feature validation and List<Feature> to add to this create request
  // @ExistentMaker
  private Long makerId;

  // @ExistentModel
  private Long modelId;

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
  @ValidUpholstery private String upholstery;
  @Builder.Default private boolean hasAccident = false;
}
