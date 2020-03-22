package bg.autohouse.web.models.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
  @NotBlank(message = "Username cannot be blank.")
  private String username;

  @NotBlank(message = "Password cannot be blank.")
  private String password;
}
