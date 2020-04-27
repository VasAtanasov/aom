package bg.autohouse.service.services;

import bg.autohouse.data.projections.geo.LocationId;
import java.util.List;

public interface LocationService {
  List<LocationId> loadAllLocations();
}
