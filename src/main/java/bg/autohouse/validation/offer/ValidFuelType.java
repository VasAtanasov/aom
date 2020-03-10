package bg.autohouse.validation.offer;

import static bg.autohouse.validation.ValidationMessages.CODE_MAKER_NAME_BLANK;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NotBlank(message = "{" + CODE_MAKER_NAME_BLANK + "}")
@NotNull()
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidFuelType {

  int MIN_LENGTH = 1;
  int MAX_LENGTH = 32;

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
