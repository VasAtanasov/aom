package bg.autohouse.service.services;

import bg.autohouse.service.models.MakerServiceModel;
import java.util.List;

public interface MakerService {
  MakerServiceModel getModelsForMaker(Long id);

  List<MakerServiceModel> getAllMakers();

  boolean isMaker(Long id);
}
