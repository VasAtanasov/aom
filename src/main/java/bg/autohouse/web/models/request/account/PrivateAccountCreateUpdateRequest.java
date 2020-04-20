package bg.autohouse.web.models.request.account;

import bg.autohouse.validation.account.ValidAccountType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivateAccountCreateUpdateRequest {
  @NotNull @NotBlank private String firstName;
  @NotNull @NotBlank private String lastName;
  private String displayName;
  private String description;
  private String contactDetailsPhoneNumber;
  private String contactDetailsWebLink;
  @ValidAccountType private String accountType;
}
