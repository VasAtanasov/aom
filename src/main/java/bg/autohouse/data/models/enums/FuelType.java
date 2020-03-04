package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FuelType implements Textable {
  GASOLINE("Gasoline"),
  DIESEL("Diesel"),
  ETHANOL("Ethanol"),
  ELECTRIC("Electric"),
  HYDROGEN("Hydrogen"),
  LPG("LPG"),
  CNG("CNG"),
  ELECTRIC_GASOLINE("Electric/Gasoline"),
  OTHERS("Others"),
  ELECTRIC_DIESEL("Electric/Diesel");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
