package bg.autohouse.web.validations.annotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MatchingFieldsValidator
    implements ConstraintValidator<MatchingFieldsConstraint, Object> {
  public static final String FIELDS_ARE_NOT_MATCHING = "Fields are not matching.";
  private String[] fields;

  @Override
  public void initialize(MatchingFieldsConstraint constraintAnnotation) {
    fields = constraintAnnotation.fields();
  }

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context) {
    List<Field> classFields = extractFieldsFromObject(obj);
    boolean areMatching = allFieldsAreMatching(classFields, obj);
    if (!areMatching) {
      addConstraintViolations(context);
    }
    return areMatching;
  }

  /**
   * Adds ConstraintViolations to all fields
   *
   * @param context ConstraintValidatorContext
   */
  private void addConstraintViolations(ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    for (String field : fields) {
      context
          .buildConstraintViolationWithTemplate(FIELDS_ARE_NOT_MATCHING)
          .addPropertyNode(field)
          .addConstraintViolation();
    }
  }

  /**
   * Validates if the fields marked by @MatchingFieldsConstraint have equal values
   *
   * @param fields the fields that need to match
   * @param obj the object from which the fields come
   * @return if all fields have the same value, otherwise false
   */
  private boolean allFieldsAreMatching(List<Field> fields, Object obj) {
    for (Field field : fields) {
      for (Field field1 : fields) {
        try {
          Object f1 = field.get(obj);
          Object f2 = field1.get(obj);
          if ((f1 == null || f2 == null) || !f1.equals(f2)) {
            return false;
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return true;
  }

  /**
   * Extracts the needed fields from the given Object
   *
   * @param obj Object from which the fields are extracted
   * @return List of desired fields
   */
  private List<Field> extractFieldsFromObject(Object obj) {
    Class<?> aClass = obj.getClass();
    List<Field> classFields = new ArrayList<>();
    for (String field : fields) {
      try {
        Field declaredField = aClass.getDeclaredField(field);
        declaredField.setAccessible(true);
        classFields.add(declaredField);
      } catch (NoSuchFieldException e) {
        log.error(String.format("Field (%s) doesn't exist.", field));
        e.printStackTrace();
      }
    }
    return classFields;
  }
}
