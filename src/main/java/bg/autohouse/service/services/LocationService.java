package bg.autohouse.service.services;

import bg.autohouse.data.projections.geo.LocationId;
import bg.autohouse.web.models.request.ProvinceLocationsCreateRequest;

import java.util.List;

public interface LocationService {
  List<LocationId> loadAllLocations();

  int createProvincesBulk(List<ProvinceLocationsCreateRequest> provincesRequest);
}
