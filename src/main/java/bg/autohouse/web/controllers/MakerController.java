package bg.autohouse.web.controllers;

import static bg.autohouse.common.Constants.*;
import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
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

    List<MakerResponseModel> makers =
        makerService.getAllMakers().stream()
            .map(model -> modelMapper.map(model, MakerResponseModel.class))
            .collect(Collectors.toUnmodifiableList());

    return handleRequestSuccess(toMap("makers", makers), REQUEST_SUCCESS);
  }

  @PostMapping(
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createMaker(@Valid @RequestBody MakerCreateRequestModel createRequest) {
    // TODO implement create Maker
    return ResponseEntity.ok().build();
  }

  // TODO create maker with list of models

  @GetMapping(
      value = "/{makerId}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getMakerById(@Valid @PathVariable Long makerId) {

    MakerServiceModel model = makerService.getOne(makerId);

    MakerResponseModel maker = modelMapper.map(model, MakerResponseModel.class);

    return handleRequestSuccess(toMap("maker", maker), REQUEST_SUCCESS);
  }

  @GetMapping(
      value = "/{makerId}/models",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getModelsByMakerId(@Valid @PathVariable Long makerId) {

    MakerResponseWrapper makerResponseModel =
        modelMapper.map(makerService.getOne(makerId), MakerResponseWrapper.class);

    List<ModelResponseModel> models =
        modelMapper.mapAll(makerService.getModelsForMaker(makerId), ModelResponseModel.class);

    makerResponseModel.setModels(models);

    return handleRequestSuccess(makerResponseModel, REQUEST_SUCCESS);
  }

  @PostMapping(
      value = "/{makerId}/models",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createModel(
      @PathVariable Long makerId, @Valid @RequestBody ModelCreateRequestModel createRequest) {
    // TODO refactor to return the model not the maker object
    ModelServiceModel modelServiceModel = modelMapper.map(createRequest, ModelServiceModel.class);

    MakerServiceModel updatedMaker = makerService.addModelToMaker(makerId, modelServiceModel);

    MakerResponseModel response = modelMapper.map(updatedMaker, MakerResponseModel.class);

    String message =
        String.format(MODEL_CREATE_SUCCESS, createRequest.getName(), updatedMaker.getName());

    return handleCreateSuccess(response, message, "/api/makers");
  }
}
