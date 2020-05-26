package bg.autohouse.service.services;

import bg.autohouse.data.projections.geo.ProvinceIdName;
import bg.autohouse.service.models.geo.ProvinceServiceModel;
import java.util.List;

public interface ProvinceService {
  List<ProvinceIdName> loadAllProvinces();

  ProvinceServiceModel loadProvinceById(Long id);
}
