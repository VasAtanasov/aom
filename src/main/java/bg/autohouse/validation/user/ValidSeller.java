package bg.autohouse.validation.user;

import static bg.autohouse.validation.ValidationMessages.INVALID_SELLER;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = SellerValidator.class)
@Documented
public @interface ValidSeller {

  String message() default INVALID_SELLER;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
