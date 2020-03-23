package bg.autohouse.web.models.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsUpdateRequest {

  @NotBlank(message = "First name is required field.")
  private String firstName;

  @NotBlank(message = "Last name is required field.")
  private String lastName;
}
