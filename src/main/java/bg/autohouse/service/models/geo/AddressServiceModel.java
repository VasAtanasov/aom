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
  private Long locationId;
  private Long locationProvinceId;
  private String locationCity;
  private String locationCityRegion;
  private String street;
  private String accountId;
  private Integer locationPostalCode;
}
