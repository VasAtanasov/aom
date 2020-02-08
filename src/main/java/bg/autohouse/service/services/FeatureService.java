package bg.autohouse.service.services;

import bg.autohouse.service.models.FeatureServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FeatureService {
    List<FeatureServiceModel> getAllFeatures();

    FeatureServiceModel getById(Long id);
}
