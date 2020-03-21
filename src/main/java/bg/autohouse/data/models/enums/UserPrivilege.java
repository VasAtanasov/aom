package bg.autohouse.data.models.enums;

import bg.autohouse.data.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserPrivilege {
  CREATE_OFFER(User.Role.USER);

  private final User.Role role;
}
