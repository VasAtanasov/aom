package bg.autohouse.service.validations;

import bg.autohouse.errors.RequiredFieldMissing;
import bg.autohouse.service.validations.annotations.Required;
import bg.autohouse.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class FieldValidator {

  private static final Class<Required> annotationClass = Required.class;

  public void validate(Object object) {
    Class<?> refClass = object.getClass();
    Stream<Field> fieldsStream = getRequiredFieldsStream(refClass);
    List<Field> fields = new ArrayList<>();
    if (refClass.isAnnotationPresent(annotationClass)) {
      fields.addAll(fieldsStream.collect(Collectors.toList()));
    } else {
      fields.addAll(
          fieldsStream
              .filter(f -> f.isAnnotationPresent(annotationClass))
              .collect(Collectors.toList()));
    }
    fields.forEach(
        field ->
            RequiredFieldMissing.assertNotNull(getValueOfField(object, field), field.getName()));
  }

  private Stream<Field> getRequiredFieldsStream(Class<?> clazz) {
    return ReflectionUtil.fieldsOfClass(true).apply(clazz).peek(field -> field.setAccessible(true));
  }

  private static Object getValueOfField(Object obj, Field field) {
    return field == null ? null : ReflectionUtils.getField(field, obj);
  }
}
