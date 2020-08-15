package bg.autohouse.web.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class LocationCreateRequest {
  private Long id;
  @NonNull private String country;
  @NonNull private String cityRegion;
  @NonNull private String city;
  @NonNull private Integer postalCode;
  private double geoLatitude;
  private double geoLongitude;
  @NonNull private String mapsUrl;
}
