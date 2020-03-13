package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.CheckboxCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@CheckboxCriteria
public enum Upholstery implements Textable {
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
