package bg.autohouse.web.validations.annotations;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = MatchingFieldsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchingFieldsConstraint {

  String[] fields();

  String message() default "Fields aren't matching.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
