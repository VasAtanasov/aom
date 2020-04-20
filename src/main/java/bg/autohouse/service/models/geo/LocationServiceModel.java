package bg.autohouse.service.models.geo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationServiceModel {
  private String locationName;
  private String city;
  private String cityRegion;
  private Integer postalCode;
  private double geoLatitude;
  private double geoLongitude;
  private String mapsUrl;
}
