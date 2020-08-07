package bg.autohouse.service.models.user;

import bg.autohouse.data.models.User;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserRowServiceModel {
  private UUID id;
  private String username;
  private boolean hasAccount;
  private boolean enabled;

  public UserRowServiceModel(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.hasAccount = user.isHasAccount();
    this.enabled = user.isEnabled();
  }
}
