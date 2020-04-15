package bg.autohouse.web.models.request.account;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateRequest {
  private String firstName;
  private String lastName;
  private String displayedName;
  private String description;
  private ContactDetailsModel contactDetails;
}
