package bg.autohouse.service.models.user;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.models.offer.Offer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.*;

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
          case ADMIN:
            return t1.equals(Role.ROOT) ? -1 : 1;
          case USER:
            return -1;
          default:
            return -1;
        }
      };

  private String userId;
  private String username;
  private boolean hasAccount;
  private List<Role> roles;
  private String role;
  private String token;
  private List<UUID> favorites;

  @Builder
  public AuthorizedUserServiceModel(User user, String token) {
    this.userId = user.getId().toString();
    this.username = user.getUsername();
    this.hasAccount = user.isHasAccount();
    this.roles = new ArrayList<>(user.getRoles());
    this.role = user.getRoles().stream().max(SYSTEM_ROLE_COMPARATOR).map(r -> r.name()).orElse("");
    this.token = token;
    this.favorites = user.getFavorites().stream().map(Offer::getId).collect(Collectors.toList());
  }
}
