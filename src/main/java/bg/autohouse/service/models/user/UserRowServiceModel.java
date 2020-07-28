package bg.autohouse.service.models.user;

import bg.autohouse.data.models.enums.Role;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRowServiceModel {
  private UUID id;
  private String username;
  private boolean hasAccount;
  private List<Role> roles;
}
