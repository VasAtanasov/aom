package bg.autohouse.web.models.response.user;

import bg.autohouse.data.models.enums.Role;
import java.util.List;
import lombok.Getter;

@Getter
public class AuthorizedUserResponseModel {

  private String username;
  private boolean hasAccount;
  private List<Role> roles;
  private String role;
  private String token;
}
