package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
  BLACK("Black"),
  GRAY("Gray"),
  CREAM("Cream"),
  LIGHT_BROWN("Light Brown"),
  DARK_BROWN("Dark Brown"),
  DARK_RED("Dark Red"),
  RED("Red"),
  DARK_BLUE("Dark Blue"),
  LIGHT_BLUE("Light Blue"),
  WHITE("White"),
  ORANGE("Orange"),
  SILVER("Silver"),
  GOLD("Gold");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
