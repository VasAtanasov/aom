package com.github.vaatech.aom.commons.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.github.vaatech.aom.commons.validation.ValidationMessages.MAKER_NAME_BLANK;
import static com.github.vaatech.aom.commons.validation.ValidationMessages.MAKER_NAME_LENGTH;
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
