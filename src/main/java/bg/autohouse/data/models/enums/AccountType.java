package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.annotations.CheckboxCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@CheckboxCriteria
public enum AccountType implements Textable {
  PRIVATE("Private"),
  DEALER("Dealer");

  private final String text;

  @Override
  public String toString() {
    return text;
  }

  public int resolveMaxOffersCount() {
    switch (this) {
      case DEALER:
        return 200;
      case PRIVATE:
        return 5;
      default:
        return 3;
    }
  }
}
