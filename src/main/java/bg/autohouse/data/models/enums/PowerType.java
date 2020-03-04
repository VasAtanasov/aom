package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PowerType {
  HP("kw"),
  KW("hp");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
