package bg.autohouse.service.validations;

import bg.autohouse.service.validations.annotations.MatchingFieldsConstraint;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class MatchingFieldsValidator implements ConstraintValidator<MatchingFieldsConstraint, Object> {

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
                    .buildConstraintViolationWithTemplate("Fields are not matching.")
                    .addPropertyNode(field).addConstraintViolation();
        }
    }

    /**
     * Validates if the fields marked by @MatchingFieldsConstraint have equal values
     *
     * @param fields the fields that need to match
     * @param obj    the object from which the fields come
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
                e.printStackTrace();
            }
        }

        return classFields;
    }
}
