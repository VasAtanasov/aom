package bg.autohouse.service.services;

import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface MakerService {
  MakerServiceModel getModelsForMaker(Long id);

  List<MakerServiceModel> getAllMakers();

  boolean isMaker(Long id);

  MakerServiceModel addModelToMaker(@NotNull Long makerId, ModelServiceModel modelServiceModel);
}
