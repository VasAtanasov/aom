package bg.autohouse.web.models.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PasswordReset {
  @NotBlank(message = "token is required")
  private String token;

  @NotBlank(message = "Password is required")
  private String password;
}
