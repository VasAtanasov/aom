package bg.autohouse.service.models.offer;

import bg.autohouse.data.models.enums.*;
import bg.autohouse.data.models.enums.Feature;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferEditServiceModel {
  private String id;
  private Integer price;
  private String description;
  private String primaryPhotoKey;
  private String contactDetailsPhoneNumber;
  private String contactDetailsWebLink;
  private String locationPostalCode;
  private String vehicleId;
  private String vehicleMakerName;
  private String vehicleModelName;
  private String vehicleTrim;
  private Integer vehicleYear;
  private Integer vehicleMileage;
  private Integer vehicleDoors;
  private State vehicleState;
  private BodyStyle vehicleBodyStyle;
  private Transmission vehicleTransmission;
  private Drive vehicleDrive;
  private Color vehicleColor;
  private FuelType vehicleFuelType;
  private String vehicleHasAccident;
  private List<Feature> vehicleFeatures;
}
