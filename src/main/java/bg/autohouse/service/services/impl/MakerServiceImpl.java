package bg.autohouse.service.services.impl;

import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.errors.MakerNotFoundException;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.services.MakerService;
import bg.autohouse.util.ModelMapperWrapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MakerServiceImpl implements MakerService {

  private final MakerRepository makerRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
  public MakerServiceModel getModelsForMaker(Long id) {
    return makerRepository
        .findById(id)
        .map(maker -> modelMapper.map(maker, MakerServiceModel.class))
        .orElseThrow(MakerNotFoundException::new);
  }

  @Override
  public List<MakerServiceModel> getAllMakers() {
    return makerRepository.findAll().stream()
        .map(maker -> modelMapper.map(maker, MakerServiceModel.class))
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public boolean isMaker(Long id) {
    return makerRepository.existsById(id);
  }
}
