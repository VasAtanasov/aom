package bg.autohouse.data.projections.geo;

import java.util.List;

public interface ProvinceLocations {
  Long getId();

  String getName();

  List<LocationDetails> getLocations();

  public interface LocationDetails {
    Long getId();

    String getCity();

    String getCityRegion();

    Integer getPostalCode();
  }
}
