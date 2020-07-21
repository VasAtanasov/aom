package bg.autohouse.web.models.response.user;

import bg.autohouse.data.models.enums.Role;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AuthorizedUserResponseModel {
  private String userId;
  private String username;
  private boolean hasAccount;
  private List<Role> roles;
  private String role;
  private String token;
  private List<UUID> favorites;
}
