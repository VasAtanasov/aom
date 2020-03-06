package bg.autohouse.service.services;

import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import java.util.List;
import javax.annotation.Nonnull;

public interface MakerService {
  MakerServiceModel getOne(Long id);

  List<MakerServiceModel> getAllMakers();

  boolean isMaker(Long id);

  MakerServiceModel addModelToMaker(@Nonnull Long makerId, ModelServiceModel modelServiceModel);

  List<ModelServiceModel> getModelsForMaker(@Nonnull Long makerId);
}
