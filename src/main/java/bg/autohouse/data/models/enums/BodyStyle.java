package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SelectCriteria
public enum BodyStyle implements Textable {
  COUPE("Coupe"),
  CONVERTIBLE("Convertible"),
  CROSSOVER("Crossover"),
  HATCHBACK("Hatchback"),
  MINIVAN("Minivan"),
  PICKUP("Pickup"),
  SEDAN("Sedan"),
  SUV("SUV"),
  VAN("Van"),
  WAGON("Wagon");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
