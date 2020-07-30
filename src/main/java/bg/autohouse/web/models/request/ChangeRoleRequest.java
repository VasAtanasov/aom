package bg.autohouse.web.models.request;

import bg.autohouse.validation.ValidationMessages;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleRequest {

  @NotNull(message = ValidationMessages.BLANK_USER_ID)
  private UUID userId;

  @NotBlank(message = ValidationMessages.BLANK_CURRENT_ROLE)
  private String currentRole;

  @NotBlank(message = ValidationMessages.BLANK_NEW_ROLE)
  private String newRole;
}
