package bg.autohouse.web.models.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LocationCreateRequest {
  @NonNull private String city;
  @NonNull private String cityRegion;
  @NonNull private String country;
  @NonNull private Integer postalCode;
  @NonNull private String mapsUrl;
  private double geoLatitude;
  private double geoLongitude;
}
