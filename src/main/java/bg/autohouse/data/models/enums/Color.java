package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SelectCriteria
public enum Color implements Textable {
  // TODO entity color (Colour groupName; displayName(entered by user))
  BLACK("Black"),
  GRAY("Gray"),
  SILVER("Silver"),
  RED("Red"),
  WHITE("White"),
  BLUE("Blue"),
  GREEN("Green"),
  BROWN("Brown"),
  GOLD("Gold"),
  PURPLE("Purple"),
  TEAL("Teal"),
  ORANGE("Orange"),
  YELLOW("YELLOW");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
