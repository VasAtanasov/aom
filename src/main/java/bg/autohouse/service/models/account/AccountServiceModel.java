package bg.autohouse.service.models.account;

import bg.autohouse.service.models.ContactDetailsServiceModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountServiceModel {
  private String firstName;
  private String lastName;
  private String displayedName;
  private String description;
  private ContactDetailsServiceModel contactDetails;
}
