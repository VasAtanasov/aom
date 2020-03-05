package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortOption {
  LATEST("Latest offers first", "auditCreatedOn,desc"),
  PRICE_ASC("Price ascending", "price"),
  PRICE_DSC("Price descending", "price,desc"),
  MILEAGE_ASC("Mileage ascending", "vehicleMileage"),
  MILEAGE_DSC("Mileage descending", "vehicleMileage,desc"),
  POWER_ASC("Power ascending", "vehicleEnginePower"),
  POWER_DSC("Power descending", "vehicleEnginePower,desc"),
  REGISTRATION_ASC("First registration ascending", "vehicleYear"),
  REGISTRATION_DSC("First registration descending", "vehicleYear,desc");

  private final String text;
  private final String value;

  @Override
  public String toString() {
    return text;
  }
}
