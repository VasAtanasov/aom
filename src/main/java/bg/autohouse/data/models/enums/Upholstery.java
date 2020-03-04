package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Upholstery {
  ALCANTARA("alcantara"),
  CLOTH("Cloth"),
  FULL_LEATHER("Full leather"),
  PART_LEATHER("Part leather"),
  VELOUR("Velour"),
  OTHER("Other");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
