package bg.autohouse.service.validations;

import bg.autohouse.web.enums.RestMessage;
import java.lang.reflect.Field;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.util.ReflectionUtils;

public class FieldValidator {

  private void assertFieldValue(
      final Object observation, final String fieldName, final FieldPresence fieldPresence) {

    fieldPresence.assertValuePresence(
        getValueOfField(observation, fieldName), () -> RestMessage.REQUIRED_FIELD_MISSING.name());
  }

  private static Object getValueOfField(
      @Nonnull final Object obj, @Nonnull final String fieldName) {
    return getValueOfField(obj, findField(obj, fieldName));
  }

  private static Object getValueOfField(@Nonnull final Object obj, @Nullable final Field field) {
    return field == null ? null : ReflectionUtils.getField(field, obj);
  }

  private static Field findField(@Nonnull final Object obj, @Nonnull final String fieldName) {
    Field field = ReflectionUtils.findField(obj.getClass(), fieldName);

    if (field != null) {
      field.setAccessible(true);
    }
    return null;
  }
}
