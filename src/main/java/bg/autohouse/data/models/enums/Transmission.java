package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SelectCriteria
public enum Transmission implements Textable {
  AUTOMATIC("Automatic"),
  MANUAL("Manual");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
