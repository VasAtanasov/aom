package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EuroStandard {
  EURO1("Euro 1"),
  EURO2("Euro 2"),
  EURO3("Euro 3"),
  EURO4("Euro 4"),
  EURO5("Euro 5"),
  EURO6("Euro 6");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
