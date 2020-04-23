package bg.autohouse.service.models.account;

import bg.autohouse.service.models.ContactDetailsServiceModel;
import bg.autohouse.service.models.geo.AddressServiceModel;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountServiceModel {
  private UUID id;
  private UUID ownerId;
  private String firstName;
  private String lastName;
  private String displayName;
  private int maxOffersCount;
  private String description;
  private ContactDetailsServiceModel contactDetails;
  private boolean hasImage;
  private String accountType;
  private AddressServiceModel address;
}
