package bg.autohouse.web.models.response.offer;

import java.util.List;
import lombok.*;

@Getter
@Setter
public class OfferDetailsResponseModel {
  private String id;
  private String accountId;
  private String accountUserId;
  private boolean accountUserEnabled;
  private Integer price;
  private String description;
  private String createdAt;
  private String contactDetailsPhoneNumber;
  private String contactDetailsWebLink;
  private String webLink;
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
  private List<String> vehicleFeatures;
  private int hitCount;
  private int savedCount;
  private boolean isActive;
}
