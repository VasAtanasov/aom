package bg.autohouse.service.services.impl;

import bg.autohouse.data.projections.geo.ProvinceIdName;
import bg.autohouse.data.repositories.ProvinceRepository;
import bg.autohouse.service.models.geo.ProvinceServiceModel;
import bg.autohouse.service.services.ProvinceService;
import bg.autohouse.util.ModelMapperWrapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProvinceServiceImpl implements ProvinceService {

  private final ProvinceRepository provinceRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
  public List<ProvinceIdName> loadAllProvinces() {
    return provinceRepository.getAllLocationIds();
  }

  @Override
  public ProvinceServiceModel loadProvinceById(Long id) {
    return modelMapper.map(provinceRepository.findProvinceById(id), ProvinceServiceModel.class);
  }
}
