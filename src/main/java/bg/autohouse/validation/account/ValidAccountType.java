package bg.autohouse.validation.account;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import bg.autohouse.validation.ValidationMessages;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = AccountTypeValidator.class)
@Documented
public @interface ValidAccountType {

  String message() default ValidationMessages.INVALID_ACCOUNT_TYPE;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
