package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ListingDetailDTO {
  private Long id;
  private String inclusionType;
  private String status;
  private String listingTitle;
  private String vehicleEntityName;
  private BigDecimal price;
  private String priceString;
  private BigDecimal expectedPrice;
  private Integer mileage;
  private String mileageString;
  private String vin;
  private String description;
  private String vehicleCondition;
  private Map<String, Object> unitMileage;
  private String[] options;
  private String localizedTransmission;
  private String localizedExteriorColor;
  private String localizedInteriorColor;
  private String localizedDriveTrain;
  private String localizedEngineDisplayName;
  private String localizedFuelType;
  private FuelTankCapacityDTO fuelTankCapacity;
  private String distance;
  private String rawDistance;
  private String roundedDistance;
  private List<ListingPictureDTO> pictures;
  private String entityId;
  private String powerTypeKey;
  private Integer carId;
  private String postalCode;
  private String makeName;
  private String modelName;
  private String makeId;
  private String modelId;
  private Integer year;
  private String trimName;
  private String bodyTypeGroupId;
  private String[] vehicleLeagues;
  private String localizedNumberOfDoors;
  private String localizedCargoVolume;
}
