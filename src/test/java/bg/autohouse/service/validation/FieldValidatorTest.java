package bg.autohouse.service.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import bg.autohouse.errors.RequiredFieldMissing;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.validations.FieldValidator;
import bg.autohouse.service.validations.annotations.Required;
import lombok.Getter;
import org.junit.jupiter.api.Test;

public class FieldValidatorTest {

  private FieldValidator validator = new FieldValidator();

  @Getter
  @Required
  private static class AllRequired {
    private String string1 = "string1";
    private String string2 = "string2";
    private String string3;
  }

  @Getter
  private static class SomeRequired {
    @Required private String string1;
    private String string2 = "string2";
    private String string3 = "string3";
  }

  @Test
  void when_RequiredField_present_shouldReturnList() {
    UserRegisterServiceModel model = UserRegisterServiceModel.builder().build();
    Throwable thrown = catchThrowable(() -> validator.validate(model));
    assertThat(thrown).isInstanceOf(RequiredFieldMissing.class);
  }

  @Test
  void when_RequiredField_presentOnClass_shouldReturnList() {
    Throwable thrown = catchThrowable(() -> validator.validate(new AllRequired()));
    assertThat(thrown).isInstanceOf(RequiredFieldMissing.class);
  }

  @Test
  void when_RequiredField_presentOnField_shouldReturnList() {
    Throwable thrown = catchThrowable(() -> validator.validate(new SomeRequired()));
    assertThat(thrown).isInstanceOf(RequiredFieldMissing.class);
  }
}
