package bg.autohouse.service.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import bg.autohouse.errors.RequiredFieldMissing;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.validations.FieldValidator;
import org.junit.jupiter.api.Test;

public class FieldValidatorTest {

  private FieldValidator validator = new FieldValidator();

  @Test
  void when_RequiredField_present_shouldReturnList() {
    UserRegisterServiceModel model = UserRegisterServiceModel.builder().build();
    Throwable thrown = catchThrowable(() -> validator.validate(model));
    assertThat(thrown).isInstanceOf(RequiredFieldMissing.class);
  }
}
