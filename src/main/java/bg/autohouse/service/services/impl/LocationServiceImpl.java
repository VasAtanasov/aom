package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.models.geo.Province;
import bg.autohouse.data.projections.geo.LocationId;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.ProvinceRepository;
import bg.autohouse.service.services.LocationService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.ProvinceLocationsCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LocationServiceImpl implements LocationService {

  private final LocationRepository locationRepository;
  private final ProvinceRepository provinceRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
  @Transactional(readOnly = true)
  public List<LocationId> loadAllLocations() {
    return locationRepository.getAllLocationIds();
  }

  @Override
  @Transactional
  public int createProvincesBulk(List<ProvinceLocationsCreateRequest> provincesRequest) {
    for (ProvinceLocationsCreateRequest provinceLocationsCreateRequest : provincesRequest) {
      Province province = modelMapper.map(provinceLocationsCreateRequest, Province.class);
      List<Location> locations = province.getLocations();
      province.setId(null);
      province.setLocations(new ArrayList<>());
      province = provinceRepository.save(province);
      for (Location location : locations) {
        location.setId(null);
        location.setProvince(province);
        province.getLocations().add(location);
        provinceRepository.save(province);
      }
    }
    return (int) locationRepository.count();
  }
}
