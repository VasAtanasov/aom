package bg.autohouse.service.models.geo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressServiceModel {
  private LocationServiceModel location;
  private String street;
  private String residentId;
}
