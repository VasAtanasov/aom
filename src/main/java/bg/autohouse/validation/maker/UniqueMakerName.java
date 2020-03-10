package bg.autohouse.validation.maker;

import static bg.autohouse.validation.ValidationMessages.CODE_MAKER_NAME_EXISTS;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueMakerNameValidator.class)
@Documented
public @interface UniqueMakerName {

  String message() default "{" + CODE_MAKER_NAME_EXISTS + "}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
