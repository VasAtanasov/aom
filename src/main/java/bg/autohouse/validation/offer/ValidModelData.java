package bg.autohouse.validation.offer;

import static bg.autohouse.validation.ValidationMessages.INVALID_MODEL_ID;
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

  String message() default INVALID_MODEL_ID;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
