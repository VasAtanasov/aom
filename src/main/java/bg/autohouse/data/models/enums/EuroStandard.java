package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SelectCriteria
public enum EuroStandard implements Textable {
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
