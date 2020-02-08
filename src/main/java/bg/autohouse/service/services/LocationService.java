package bg.autohouse.service.services;

import bg.autohouse.service.models.LocationServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LocationService {

    List<LocationServiceModel> getAllLocations();
}
