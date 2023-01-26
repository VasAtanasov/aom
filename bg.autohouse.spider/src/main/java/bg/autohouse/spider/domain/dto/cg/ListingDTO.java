package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.util.Map;

@Data
public class ListingDTO {
  private Long id;
  private String inclusionType;
  private String listingTitle;
  private String makeName;
  private String modelName;
  private String makeId;
  private String modelId;
  private Integer carYear;
  private String trimName;
  private String localizedTransmission;
  private String bodyTypeGroupId;
  private String bodyTypeName;
  private String salesStatus;
  private String[] options;
  private Integer mileage;
  private String mileageString;
  private Map<String, Object> unitMileage;
  private String exteriorColorName;
  private String normalizedExteriorColor;
  private Integer price;
  private String priceString;
  private Integer expectedPrice;
  private String expectedPriceString;
  private Integer savingsAmount;
  private Integer priceDifferential;
  private String priceDifferentialString;
  private String mainPictureUrl;
  private String originalPictureUrl;
  private Integer pictureCount;
  private ListingPictureDTO originalPictureData;
  private ListingPictureDTO mainPictureData;
  private String sellerCity;
  private String sellerRegion;
  private String sellerPostalCode;
  private Integer accidentCount;
  private Integer ownerCount;
  private Integer offset;
  private String localizedFuelType;
  private String localizedDoors;
  private String localizedEngineDisplayName;
}
