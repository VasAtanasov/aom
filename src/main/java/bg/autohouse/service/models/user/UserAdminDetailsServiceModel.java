package bg.autohouse.service.models.user;

import static bg.autohouse.service.models.user.AuthorizedUserServiceModel.SYSTEM_ROLE_COMPARATOR;

import bg.autohouse.data.models.User;
import bg.autohouse.service.models.account.AccountServiceModel;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminDetailsServiceModel {
  private UUID id;
  private String username;
  private boolean hasAccount;
  private boolean enabled;
  private String role;
  private AccountServiceModel account;

  @Builder
  public UserAdminDetailsServiceModel(User user, AccountServiceModel account) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.hasAccount = user.isHasAccount();
    this.enabled = user.isEnabled();
    this.role = user.getRoles().stream().max(SYSTEM_ROLE_COMPARATOR).map(r -> r.name()).orElse("");
    this.account = account;
  }
}
