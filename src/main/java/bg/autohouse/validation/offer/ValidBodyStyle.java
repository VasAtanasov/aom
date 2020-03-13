package bg.autohouse.validation.offer;

import static bg.autohouse.validation.ValidationMessages.CODE_INVALID_BODY_STYLE;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = BodyStyleValidator.class)
@Documented
public @interface ValidBodyStyle {

  String message() default "{" + CODE_INVALID_BODY_STYLE + "}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
