package bg.autohouse.errors;

import bg.autohouse.errors.modela.ValidationError;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredFieldMissing extends RuntimeException {

  private static final long serialVersionUID = -7340549728833321194L;

  @Getter private ValidationError validationError;

  protected RequiredFieldMissing(final String msg) {
    super(msg);
  }

  protected RequiredFieldMissing(ValidationError validationError) {
    super(validationError.getMessage());
    this.validationError = validationError;
  }

  public static void assertNotNull(Object value, String fieldName) {
    assertNotNull(value, fieldName, () -> getDefaultMessage(fieldName));
  }

  public static void assertNotNull(Object value, String fieldName, Supplier<String> msgSupplier) {
    Objects.requireNonNull(msgSupplier, "msgSupplier is null");
    if (value == null) {
      ValidationError error = errorOf(fieldName, value, msgSupplier.get());
      throw new RequiredFieldMissing(error);
    }
  }

  public static String getDefaultMessage(String fieldName) {
    Objects.requireNonNull(fieldName, "fieldName is null");
    return "Required field missing: " + fieldName;
  }

  private static ValidationError errorOf(String fieldName, Object rejectedValue, String message) {
    return ValidationError.builder()
        .fieldName(fieldName)
        .rejectedValue(rejectedValue)
        .message(message)
        .build();
  }
}
