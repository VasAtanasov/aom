package bg.autohouse.web.models.binding;

import bg.autohouse.service.validations.annotations.MatchingFieldsConstraint;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static bg.autohouse.common.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MatchingFieldsConstraint(fields = {"password", "confirmPassword"})
public class UserRegisterBindingModel {

    @NotNull(message = EXCEPTION_EMAIL_NOT_NULL)
    @NotEmpty(message = EXCEPTION_EMAIL_NOT_EMPTY)
    @Pattern(regexp = EMAIL_PATTERN_STRING, message = EXCEPTION_EMAIL_NOT_VALID)
    private String email;

    @NotNull(message = EXCEPTION_USERNAME_NOT_NULL)
    @NotBlank(message = EXCEPTION_USERNAME_NOT_EMPTY)
    @Length(min = 3, message = EXCEPTION_USERNAME_LENGTH)
    private String username;

    @NotNull(message = EXCEPTION_PASSWORD_NOT_NULL)
    @NotEmpty(message = EXCEPTION_PASSWORD_NOT_EMPTY)
    @Length(min = 4, max = 20, message = EXCEPTION_PASSWORD_LENGTH)
    private String password;

    @NotNull(message = EXCEPTION_CONFIRM_PASSWORD_NOT_NULL)
    @NotEmpty(message = EXCEPTION_CONFIRM_PASSWORD_NOT_EMPTY)
    @Length(min = 4, max = 20, message = EXCEPTION_CONFIRM_PASSWORD_LENGTH)
    private String confirmPassword;

    @NotNull(message = EXCEPTION_FIRST_NAME_NOT_NULL)
    @NotEmpty(message = EXCEPTION_FIRST_NAME_NOT_EMPTY)
    @Length(min = 3, message = EXCEPTION_FIRST_NAME_LENGTH)
    @Pattern(regexp = NAME_PATTERN, message = EXCEPTION_FIRST_NAME_CAPITAL_LETTER)
    private String firstName;

    @NotNull(message = EXCEPTION_LAST_NAME_NOT_NULL)
    @NotEmpty(message = EXCEPTION_LAST_NAME_NOT_EMPTY)
    @Length(min = 3, message = EXCEPTION_LAST_NAME_LENGTH)
    @Pattern(regexp = NAME_PATTERN, message = EXCEPTION_LAST_NAME_CAPITAL_LETTER)
    private String lastName;

    @NotNull(message = EXCEPTION_PHONE_NUMBER_NOT_NULL)
    @NotEmpty(message = EXCEPTION_PHONE_NUMBER_NOT_EMPTY)
    @Pattern(regexp = EXCEPTION_PHONE_NUMBER_PATTERN, message = EXCEPTION_PHONE_NUMBER_NOT_VALID)
    private String phoneNumber;

}
