package bg.autohouse.web.validation.annotations.maker;

import static bg.autohouse.web.validation.MessageCodes.CODE_MAKER_NAME_BLANK;
import static bg.autohouse.web.validation.MessageCodes.CODE_MAKER_NAME_LENGTH;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NotBlank(message = "{" + CODE_MAKER_NAME_BLANK + "}")
@Size(
    message = "{" + CODE_MAKER_NAME_LENGTH + "}",
    min = MakerName.MIN_LENGTH,
    max = MakerName.MAX_LENGTH)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface MakerName {

  int MIN_LENGTH = 11;
  int MAX_LENGTH = 32;

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}