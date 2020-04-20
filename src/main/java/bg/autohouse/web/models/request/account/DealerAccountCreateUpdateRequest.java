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
public class DealerAccountCreateUpdateRequest {
  @NotNull @NotBlank private String firstName;
  @NotNull @NotBlank private String lastName;
  @NotNull @NotBlank private String displayName;
  @NotNull @NotBlank private String description;
  @NotNull @NotBlank private String contactDetailsPhoneNumber;
  private String contactDetailsWebLink;
  @NotNull private Long addressLocationId;
  @NotNull @NotBlank private String addressStreet;
  @ValidAccountType private String accountType;
}
