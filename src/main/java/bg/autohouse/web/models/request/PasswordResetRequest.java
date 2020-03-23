package bg.autohouse.web.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid.")
  private String email;
}
