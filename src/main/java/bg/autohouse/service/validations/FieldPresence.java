package bg.autohouse.service.validations;

import bg.autohouse.errors.RequiredFieldMissing;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface FieldPresence {

  boolean nonNullValueRequired();

  default boolean isValidValue(@Nullable final Object value) {
    if (nonNullValueRequired()) {
      return value != null;
    }
    return true;
  }

  default void assertValuePresence(
      @Nullable final Object value,
      @Nonnull final Supplier<String> errorMessageWhenRequiredFieldMissing) {

    if (nonNullValueRequired()) {
      RequiredFieldMissing.assertValueIsNotNull(value, errorMessageWhenRequiredFieldMissing);
    }
  }
}
