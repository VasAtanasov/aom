package bg.autohouse.validation.offer;

import static bg.autohouse.validation.ValidationMessages.CODE_INVALID_MODEL_DATA;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ModelDataValidator.class)
@Documented
public @interface ValidModelData {

  String message() default "{" + CODE_INVALID_MODEL_DATA + "}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
