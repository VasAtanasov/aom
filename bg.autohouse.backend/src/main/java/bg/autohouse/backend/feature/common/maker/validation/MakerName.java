package bg.autohouse.backend.feature.common.maker.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static bg.autohouse.backend.domain.validation.ValidationMessages.MAKER_NAME_BLANK;
import static bg.autohouse.backend.domain.validation.ValidationMessages.MAKER_NAME_LENGTH;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank(message = MAKER_NAME_BLANK)
@Size(message = MAKER_NAME_LENGTH, min = MakerName.MIN_LENGTH, max = MakerName.MAX_LENGTH)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface MakerName {
  int MIN_LENGTH = 1;
  int MAX_LENGTH = 32;

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
