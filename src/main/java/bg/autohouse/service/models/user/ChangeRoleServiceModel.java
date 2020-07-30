package bg.autohouse.service.models.user;

import bg.autohouse.data.models.enums.Role;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleServiceModel {
  private UUID userId;
  private Role currentRole;
  private Role newRole;
}
