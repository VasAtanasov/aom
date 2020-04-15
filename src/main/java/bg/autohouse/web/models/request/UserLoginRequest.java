package bg.autohouse.web.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class UserLoginRequest {
  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid.")
  private String username;

  @NotBlank(message = "Password cannot be blank.")
  private String password;
}
