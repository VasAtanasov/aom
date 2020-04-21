package bg.autohouse.audit.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserLoginEvent {
  private String userId;
}
