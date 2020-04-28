package bg.autohouse.web.models.request;

import bg.autohouse.web.validations.annotations.MatchingFieldsConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

// TODO validate password length
// TODO validate email
@Data
@Builder
@MatchingFieldsConstraint(fields = {"password", "confirmPassword"})
public class UserRegisterRequest {
  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid.")
  private String username;

  @NotBlank(message = "Password field is required")
  private String password;

  private String confirmPassword;
}
