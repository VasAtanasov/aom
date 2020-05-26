package bg.autohouse.service.services.impl;

import bg.autohouse.data.projections.geo.ProvinceIdName;
import bg.autohouse.data.repositories.ProvinceRepository;
import bg.autohouse.service.services.ProvinceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProvinceServiceImpl implements ProvinceService {

  private final ProvinceRepository provinceRepository;

  @Override
  public List<ProvinceIdName> loadAllProvinces() {
    return provinceRepository.getAllLocationIds();
  }
}
