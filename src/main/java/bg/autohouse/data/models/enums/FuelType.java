package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SelectCriteria
public enum FuelType implements Textable {
  GASOLINE("Gasoline"),
  FLEX_FUEL_VEHICLE("Flex Fuel Vehicle"),
  DIESEL("Diesel"),
  HYBRID("Hybrid"),
  BIODIESEL("Biodiesel"),
  ELECTRIC("Electric"),
  COMPRESSED_NATURAL_GAS("Compressed Natural Gas"),
  PLUG_IN_HYBRID("Plug-In Hybrid");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
