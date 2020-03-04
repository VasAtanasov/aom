package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
  NEW("New"),
  USED("Used"),
  ACCIDENT("Accident");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
