package bg.autohouse.service.services.impl;

import bg.autohouse.data.repositories.FeatureRepository;
import bg.autohouse.service.models.FeatureServiceModel;
import bg.autohouse.service.services.FeatureService;
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
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;
    private final ModelMapperWrapper modelMapper;

    @Override
    @Cacheable(cacheNames = "features", sync = true)
    public List<FeatureServiceModel> getAllFeatures() {
        return featureRepository.findAll()
                .stream()
                .map(feature -> modelMapper.map(feature, FeatureServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public FeatureServiceModel getById(Long id) {
        return modelMapper.map(featureRepository.getOne(id), FeatureServiceModel.class);
    }
}
