package bg.autohouse.service.services.impl;

import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.service.models.LocationServiceModel;
import bg.autohouse.service.services.LocationService;
import bg.autohouse.util.ModelMapperWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final ModelMapperWrapper modelMapper;

    @Override
    @Cacheable(cacheNames = "locations", sync = true)
    public List<LocationServiceModel> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(location -> modelMapper.map(location, LocationServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }
}
