package bg.autohouse.service.services.impl;

import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.service.models.MakerNameServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.services.MakerService;
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
public class MakerServiceImpl implements MakerService {

    private final MakerRepository makerRepository;
    private final ModelMapperWrapper modelMapper;

    @Override
    public MakerServiceModel getModelsForMaker(Long id) {
        return makerRepository.findById(id)
                .map(maker -> modelMapper.map(maker, MakerServiceModel.class))
                .orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "maker", sync = true)
    public MakerServiceModel findMakerByName(String name) {
        return makerRepository.findByName(name)
                .map(maker -> modelMapper.map(maker, MakerServiceModel.class))
                .orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "makers", sync = true)
    public List<MakerNameServiceModel> getAllMakers() {
        return makerRepository.findAll()
                .stream()
                .map(maker -> modelMapper.map(maker, MakerNameServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isMaker(Long id) {
        return makerRepository.existsById(id);
    }
}
