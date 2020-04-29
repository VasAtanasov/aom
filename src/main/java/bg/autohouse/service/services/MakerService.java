package bg.autohouse.service.services;

import bg.autohouse.service.models.MakerModelServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import bg.autohouse.service.models.ModelTrimsServicesModel;
import java.util.List;
import javax.annotation.Nonnull;

public interface MakerService {
  MakerModelServiceModel getOne(Long id);

  List<MakerServiceModel> getAllMakers();

  List<MakerModelServiceModel> getAllMakerWithModels();

  boolean isMaker(Long id);

  MakerServiceModel addModelToMaker(@Nonnull Long makerId, ModelServiceModel modelServiceModel);

  List<ModelTrimsServicesModel> getMakerModelsTrims(Long makerId);

  MakerServiceModel createMaker(@Nonnull MakerServiceModel makerServiceModel);

  ModelTrimsServicesModel getModel(String makerName, String modelName);
}
