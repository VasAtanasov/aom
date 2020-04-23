package bg.autohouse.service.models.user;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthorizedUserServiceModel {

  private static final Comparator<Role> SYSTEM_ROLE_COMPARATOR =
      (role, t1) -> {
        if (role.equals(t1)) {
          return 0;
        }

        switch (role) {
          case ROOT:
            return 1;
          case ADMIN: // we know it's not equal or null
            return t1.equals(Role.ADMIN) ? -1 : 1;
          case USER: // we know it's not equal, and t1 is not null, so must be greater
            return -1;
          default: // should never happen, but in case something strange added in future, put it
            // last
            return -1;
        }
      };

  private String userId;
  private String username;
  private boolean hasAccount;
  private List<Role> roles;
  private String role;
  private String token;

  @Builder
  public AuthorizedUserServiceModel(User user, String token) {
    this.userId = user.getId().toString();
    this.username = user.getUsername();
    this.hasAccount = user.isHasAccount();
    this.roles = new ArrayList<>(user.getRoles());
    this.role = user.getRoles().stream().max(SYSTEM_ROLE_COMPARATOR).map(r -> r.name()).orElse("");
    this.token = token;
  }
}
