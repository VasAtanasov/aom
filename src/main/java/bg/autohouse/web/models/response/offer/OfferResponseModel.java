package bg.autohouse.web.models.response.offer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferResponseModel {
  private String id;
  private Integer price;
  private String locationCity;
  private String locationId;
  private String createdAt;
  private String primaryPhotoKey;
  private String vehicleMakerName;
  private Long vehicleMakerId;
  private String vehicleModelName;
  private Long vehicleModelId;
  private String vehicleTrim;
  private Integer vehicleYear;
  private Integer vehicleMileage;
  private Integer vehicleDoors;
  private String vehicleState;
  private String vehicleBodyStyle;
  private String vehicleTransmission;
  private String vehicleDrive;
  private String vehicleColor;
  private String vehicleFuelType;
  private String vehicleHasAccident;
  private int hitCount;
  private int savedCount;
  private boolean isActive;
}
