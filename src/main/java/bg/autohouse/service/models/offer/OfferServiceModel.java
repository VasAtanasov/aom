package bg.autohouse.service.models.offer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferServiceModel {
  private String id;
  private String vehicleState;
  private Integer vehicleYear;
  private String vehicleMakerName;
  private String vehicleModelName;
  private String vehicleFuelType;
  private Integer vehicleMileage;
  private Integer price;
  private String vehicleTransmission;
  private String locationName;
  private String vehicleBodyStyle;
  private String createdAt;
  private String thumbnail;
}
