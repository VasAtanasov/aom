package bg.autohouse.service.services;

import bg.autohouse.data.projections.geo.ProvinceIdName;
import java.util.List;

public interface ProvinceService {
  List<ProvinceIdName> loadAllProvinces();
}
