package bg.autohouse.service.services.impl;

import bg.autohouse.data.projections.geo.LocationId;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.service.services.LocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LocationServiceImpl implements LocationService {

  private final LocationRepository locationRepository;

  @Override
  public List<LocationId> loadAllLocations() {
    return locationRepository.getAllLocationIds();
  }
}
