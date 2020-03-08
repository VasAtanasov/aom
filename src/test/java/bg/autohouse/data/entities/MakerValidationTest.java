package bg.autohouse.data.entities;

import bg.autohouse.HibernateValidatorTest;
import bg.autohouse.data.models.Maker;
import bg.autohouse.web.validation.ValidationMessages;
import org.junit.jupiter.api.Test;

public class MakerValidationTest extends HibernateValidatorTest {
  private static final String NAME = "name";

  @Test
  void whenMakerName_isNull_shouldInvalidateWithMessage() {
    Maker maker = Maker.of(null);
    isInvalid(maker);
    assertMessage(maker, NAME, ValidationMessages.MAKER_NAME_BLANK);
  }

  @Test
  void whenMakerName_isEmpty_shouldInvalidateWithMessage() {
    Maker maker = Maker.of("");
    isInvalid(maker);
    assertMessage(maker, NAME, ValidationMessages.MAKER_NAME_BLANK);
  }

  @Test
  void whenMakerName_isValid_shouldInvalidateWithMessage() {
    Maker maker = Maker.of("Audi");
    isValid(maker);
  }
}
