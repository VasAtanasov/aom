package bg.autohouse.web.models.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferResponseModel {
  private String id;
  private String vehicleState;
  private Integer vehicleManufactureDateYear;
  private String vehicleMakerName;
  private String vehicleModelName;
  private String vehicleEngineFuelType;
  private Integer vehicleMileage;
  private Integer price;
  private String vehicleTransmission;
  private String locationName;
  private String vehicleBodyStyle;
  private String createdAT;
  private String thumbnail;
  private Integer vehicleEnginePower;
}
