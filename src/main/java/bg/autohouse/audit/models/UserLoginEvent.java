package bg.autohouse.audit.models;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserLoginEvent {
  private UUID userId;
}
