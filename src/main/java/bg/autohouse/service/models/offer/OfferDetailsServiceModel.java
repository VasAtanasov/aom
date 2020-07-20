package bg.autohouse.service.models.offer;

import bg.autohouse.data.models.enums.Feature;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDetailsServiceModel {
  private String id;
  private Integer price;
  private String description;
  private String createdAt;
  private String primaryPhotoKey;
  private String contactDetailsPhoneNumber;
  private String contactDetailsWebLink;
  private String accountDisplayName;
  private String accountFirstName;
  private String accountLastName;
  private String locationCity;
  private String vehicleMakerName;
  private String vehicleModelName;
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
  private List<Feature> vehicleFeatures;
}
