package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Transmission implements Textable {
  AUTOMATIC("Automatic"),
  SEMI_AUTOMATIC("Semi-automatic"),
  MANUAL("Manual");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
