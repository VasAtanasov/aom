package bg.autohouse.service.services;

import bg.autohouse.service.models.geo.ProvinceServiceModel;
import java.util.List;

public interface ProvinceService {
  List<ProvinceServiceModel> loadAllProvinces();

  ProvinceServiceModel loadProvinceById(Long id);
}
