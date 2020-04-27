package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SelectCriteria
public enum BodyStyle implements Textable {
  CROSSOVER("Crossover"),
  SUV("SUV"),
  VAN("Van"),
  COUPE("Coupe"),
  CONVERTIBLE("Convertible"),
  SEDAN("Sedan"),
  PICKUP("Pickup"),
  MINIVAN("Minivan"),
  WAGON("Wagon"),
  HATCHBACK("Hatchback"),
  // TOOD remove below types
  MPV("MPV"),
  CPO("CPO"),
  HYBRID("Hybrid"),
  LUXURY("Luxury"),
  TRUCK("Truck"),
  SPORT("Sport"),
  OTHER("Other");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
