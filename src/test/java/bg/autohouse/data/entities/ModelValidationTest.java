package bg.autohouse.data.entities;

import bg.autohouse.HibernateValidatorTest;
import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Model;
import bg.autohouse.validation.ValidationMessages;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class ModelValidationTest extends HibernateValidatorTest {
  private static final String NAME = "name";
  private static final String MAKER = "Audi";
  private static final String MODEL = "A4";

  @Test
  void whenModelName_isNull_shouldInvalidateWithMessage() {
    Maker maker = Maker.builder().name(MAKER).build();
    Model modelNullName = Model.of(null, maker, new ArrayList<>());
    Model modelEmptyName = Model.of("", maker, new ArrayList<>());

    isInvalid(modelNullName);
    isInvalid(modelEmptyName);
    assertMessage(modelNullName, NAME, ValidationMessages.MODEL_NAME_BLANK);
    assertMessage(modelEmptyName, NAME, ValidationMessages.MODEL_NAME_BLANK);
  }

  @Test
  void whenMaker_isNull_shouldInvalidateWithMessage() {
    Model model = Model.of(MODEL, null, new ArrayList<>());

    isInvalid(model);
    assertMessage(model, "maker", ValidationMessages.MAKER_NULL);
  }

  @Test
  void whenMakerName_isValid_shouldInvalidateWithMessage() {
    Maker maker = Maker.builder().name(MAKER).build();

    Model model = Model.of(MODEL, maker, new ArrayList<>());
    isValid(maker);
    isValid(model);
  }
}
