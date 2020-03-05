package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SelectCriteria
public enum BodyStyle implements Textable {
  SUV("SUV"),
  MPV("MPV"),
  CPO("CPO"),
  HYBRID("Hybrid"),
  SEDAN("Sedan"),
  CROSSOVER("Crossover"),
  LUXURY("Luxury"),
  TRUCK("Truck"),
  PICKUP("Pickup"),
  HATCHBACK("Hatchback"),
  MINIVAN("Minivan"),
  COUPE("Coupe"),
  CONVERTIBLE("Convertible"),
  WAGON("Wagon"),
  SPORT("Sport"),
  VAN("Van"),
  OTHER("Other");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
