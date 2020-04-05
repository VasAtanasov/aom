package bg.autohouse.service.services;

import bg.autohouse.service.models.MakerModelCarsServiceModel;
import bg.autohouse.service.models.MakerModelServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import bg.autohouse.service.models.ModelTrimsServicesMode;
import java.util.List;
import javax.annotation.Nonnull;

public interface MakerService {
  MakerModelServiceModel getOne(Long id);

  List<MakerServiceModel> getAllMakers();

  List<MakerModelServiceModel> getAllMakerWithModels();

  boolean isMaker(Long id);

  MakerServiceModel addModelToMaker(@Nonnull Long makerId, ModelServiceModel modelServiceModel);

  List<ModelTrimsServicesMode> getMakerModelsTrims(Long makerId);

  List<MakerModelCarsServiceModel> getMakerModelCars(Long makerId);

  MakerServiceModel createMaker(@Nonnull MakerServiceModel makerServiceModel);
}
