package bg.autohouse.service.validations;

import bg.autohouse.errors.RequiredFieldMissing;
import bg.autohouse.service.validations.annotations.Required;
import bg.autohouse.util.ReflectionUtil;
import com.google.common.base.Predicate;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class FieldValidator {

  private static final Predicate<Field> REQUIRED_FIELD_ANNOTATION_PRESENT =
      f -> f.isAnnotationPresent(Required.class);

  public void validate(Object object) {
    Class<?> refClass = object.getClass();
    List<Field> fields = getRequiredFields(refClass);
    fields.forEach(
        field ->
            RequiredFieldMissing.assertNotNull(getValueOfField(object, field), field.getName()));
  }

  private List<Field> getRequiredFields(Class<?> clazz) {
    return ReflectionUtil.fieldsOfClass(true)
        .apply(clazz)
        .filter(REQUIRED_FIELD_ANNOTATION_PRESENT)
        .peek(field -> field.setAccessible(true))
        .collect(Collectors.toList());
  }

  private static Object getValueOfField(Object obj, Field field) {
    return field == null ? null : ReflectionUtils.getField(field, obj);
  }
}
