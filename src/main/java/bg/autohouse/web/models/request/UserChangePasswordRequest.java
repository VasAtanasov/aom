package bg.autohouse.web.models.request;

import bg.autohouse.web.validations.annotations.MatchingFieldsConstraint;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MatchingFieldsConstraint(fields = {"newPassword", "confirmPassword"})
public class UserChangePasswordRequest {
  @NotBlank(message = "Old password field is required")
  private String oldPassword;

  @NotBlank(message = "New password field is required")
  private String newPassword;

  private String confirmPassword;
}
