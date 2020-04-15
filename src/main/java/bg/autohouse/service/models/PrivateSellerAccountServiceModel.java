package bg.autohouse.service.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivateSellerAccountServiceModel {
  private String firstName;
  private String lastName;
  private String displayedName;
  private String description;
  private ContactDetailsServiceModel contactDetails;
}
