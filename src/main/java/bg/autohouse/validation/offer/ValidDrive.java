package bg.autohouse.validation.offer;

import static bg.autohouse.validation.ValidationMessages.INVALID_DRIVE;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = DriveValidator.class)
@Documented
public @interface ValidDrive {

  String message() default INVALID_DRIVE;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
