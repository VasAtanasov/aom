package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Seller implements Textable {
  PRIVATE("Private"),
  DEALER("Dealer");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}
