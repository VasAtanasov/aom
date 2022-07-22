package bg.autohouse.core.domain.validation.maker;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static bg.autohouse.core.domain.validation.ValidationMessages.MODEL_NAME_BLANK;
import static bg.autohouse.core.domain.validation.ValidationMessages.MODEL_NAME_LENGTH;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank(message = MODEL_NAME_BLANK)
@Size(message = MODEL_NAME_LENGTH, min = ModelName.MIN_LENGTH, max = ModelName.MAX_LENGTH)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ModelName
{

    int MIN_LENGTH = 1;
    int MAX_LENGTH = 32;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
