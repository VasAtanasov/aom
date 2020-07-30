package bg.autohouse.service.models.user;

import static bg.autohouse.service.models.user.AuthorizedUserServiceModel.SYSTEM_ROLE_COMPARATOR;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRowServiceModel {
  private UUID id;
  private String username;
  private boolean hasAccount;
  private boolean enabled;
  private List<Role> roles;
  private String role;

  @Builder
  public UserRowServiceModel(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.hasAccount = user.isHasAccount();
    this.enabled = user.isEnabled();
    this.roles = new ArrayList<>(user.getRoles());
    this.role = user.getRoles().stream().max(SYSTEM_ROLE_COMPARATOR).map(r -> r.name()).orElse("");
  }
}
