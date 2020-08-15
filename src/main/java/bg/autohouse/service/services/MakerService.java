package bg.autohouse.service.services;

import bg.autohouse.service.models.MakerModelServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import bg.autohouse.service.models.ModelTrimsServicesModel;
import bg.autohouse.web.models.request.MakerModelsTrimsCreateRequest;

import java.util.List;

public interface MakerService {
  MakerModelServiceModel getOne(Long id);

  List<MakerModelServiceModel> getAllMakerWithModels();

  MakerServiceModel addModelToMaker(Long makerId, ModelServiceModel modelServiceModel);

  List<ModelTrimsServicesModel> getMakerModelsTrims(Long makerId);

  MakerServiceModel createMaker(MakerServiceModel makerServiceModel);

  ModelTrimsServicesModel getModel(String makerName, String modelName);

  int createMakerModelsTrimsBulk(List<MakerModelsTrimsCreateRequest> makersRequest);
}
