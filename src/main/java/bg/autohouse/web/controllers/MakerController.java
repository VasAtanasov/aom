package bg.autohouse.web.controllers;

import static bg.autohouse.common.Constants.*;
import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.common.Constants;
import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.models.MakerModelServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import bg.autohouse.service.services.MakerService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.MakerCreateRequestModel;
import bg.autohouse.web.models.request.ModelCreateRequestModel;
import bg.autohouse.web.models.response.MakerResponseModel;
import bg.autohouse.web.models.response.MakerResponseWrapper;
import bg.autohouse.web.models.response.ModelResponseModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_MAKERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MakerController extends BaseController {

  public static final String CONTROLLER_TAG = "makers";

  private final ModelMapperWrapper modelMapper;
  private final MakerService makerService;

  @GetMapping(produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getMakers() {

    List<MakerServiceModel> makerServiceModels = makerService.getAllMakers();

    List<MakerResponseModel> makers =
        makerServiceModels.stream()
            .map(model -> modelMapper.map(model, MakerResponseModel.class))
            .collect(Collectors.toUnmodifiableList());

    return handleRequestSuccess(toMap("makers", makers), REQUEST_SUCCESS);
  }

  @PostMapping(
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createMaker(@Valid @RequestBody MakerCreateRequestModel createRequest) {
    MakerServiceModel makerServiceModel = modelMapper.map(createRequest, MakerServiceModel.class);
    MakerServiceModel createdMaker = makerService.createMaker(makerServiceModel);
    return handleCreateSuccess(
        toMap("maker", modelMapper.map(createdMaker, MakerResponseModel.class)),
        String.format(Constants.MAKER_CREATE_SUCCESS, createRequest.getName()),
        WebConfiguration.URL_API_BASE + WebConfiguration.URL_MAKERS);
  }

  // TODO create maker with list of models

  @GetMapping(
      value = "/{makerId}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getMakerById(@Valid @PathVariable Long makerId) {

    MakerModelServiceModel model = makerService.getOne(makerId);

    MakerResponseWrapper maker = modelMapper.map(model, MakerResponseWrapper.class);

    return handleRequestSuccess(toMap("maker", maker), REQUEST_SUCCESS);
  }

  @GetMapping(
      value = "/{makerId}/models",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getModelsByMakerId(@Valid @PathVariable Long makerId) {

    MakerResponseWrapper makerResponseModel =
        modelMapper.map(makerService.getOne(makerId), MakerResponseWrapper.class);

    // List<ModelResponseModel> models =
    //     modelMapper.mapAll(makerService.getModelsForMaker(makerId), ModelResponseModel.class);

    // makerResponseModel.setModels(models);

    return handleRequestSuccess(makerResponseModel, REQUEST_SUCCESS);
  }

  @PostMapping(
      value = "/{makerId}/models",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createModel(
      @PathVariable Long makerId, @Valid @RequestBody ModelCreateRequestModel createRequest) {

    ModelServiceModel modelServiceModel = modelMapper.map(createRequest, ModelServiceModel.class);
    makerService.addModelToMaker(makerId, modelServiceModel);

    MakerResponseWrapper makerResponseModel =
        modelMapper.map(makerService.getOne(makerId), MakerResponseWrapper.class);

    List<ModelResponseModel> models =
        modelMapper.mapAll(makerService.getModelsForMaker(makerId), ModelResponseModel.class);

    makerResponseModel.setModels(models);

    String message =
        String.format(MODEL_CREATE_SUCCESS, createRequest.getName(), makerResponseModel.getName());

    String locationURI =
        WebConfiguration.URL_API_BASE
            + WebConfiguration.URL_MAKERS
            + "/"
            + makerId
            + "/"
            + WebConfiguration.URL_MODELS;

    return handleCreateSuccess(makerResponseModel, message, locationURI);
  }
}
