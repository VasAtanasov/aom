package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.CheckboxCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@CheckboxCriteria
public enum State implements Textable {
  NEW("New"),
  USED("Used"),
  CPO("Certified Pre-Owned");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
