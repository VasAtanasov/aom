package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SelectCriteria
public enum SortOption implements Textable {
  LATEST("createdOn,desc"),
  PRICE_ASC("price"),
  PRICE_DSC("price,desc"),
  MILEAGE_ASC("vehicleMileage"),
  MILEAGE_DSC("vehicleMileage,desc"),
  REGISTRATION_ASC("vehicleYear"),
  REGISTRATION_DSC("vehicleYear,desc");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
