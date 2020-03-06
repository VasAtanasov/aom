package bg.autohouse.web.controllers;

import static bg.autohouse.common.Constants.*;

import bg.autohouse.common.Constants;
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
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Api(tags = MakerController.CONTROLLER_TAG)
@RestController
@RequestMapping(value = WebConfiguration.URL_MAKERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MakerController extends BaseController {

  public static final String CONTROLLER_TAG = "makers";

  private final ModelMapperWrapper modelMapper;
  private final MakerService makerService;

  @ApiOperation(value = "Retrieves all makers.", response = List.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getMakers() {

    List<MakerResponseModel> makers =
        makerService.getAllMakers().stream()
            .map(model -> modelMapper.map(model, MakerResponseModel.class))
            .collect(Collectors.toUnmodifiableList());

    return handleRequestSuccess(toMap("makers", makers), REQUEST_SUCCESS);
  }

  private <T> ImmutableMap<String, List<T>> toMap(final String key, final List<T> objects) {
    return ImmutableMap.of(key, objects);
  }

  @ApiOperation(
      value = "Retrieves a maker with all its models by the given ID.",
      response = MakerResponseModel.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 404, message = Constants.EXCEPTION_MAKER_NOT_FOUND),
        @ApiResponse(code = 200, message = "OK")
      })
  @GetMapping(
      value = "/{makerId}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getMakerById(
      @ApiParam(name = "makerId", value = "The ID of the maker.", required = true)
          @Valid
          @PathVariable
          Long makerId) {

    MakerResponseModel maker =
        modelMapper.map(makerService.getOne(makerId), MakerResponseModel.class);

    return handleRequestSuccess(maker, REQUEST_SUCCESS);
  }

  @ApiOperation(
      value = "Retrieves all models for give maker id.",
      response = MakerResponseWrapper.class)
  @GetMapping(
      value = "/{makerId}/models",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getModelsByMakerId(
      @ApiParam(name = "makerId", value = "The ID of the maker.", required = true)
          @Valid
          @PathVariable
          Long makerId) {

    MakerResponseWrapper makerResponseModel =
        modelMapper.map(makerService.getOne(makerId), MakerResponseWrapper.class);

    List<ModelResponseModel> models =
        modelMapper.mapAll(makerService.getModelsForMaker(makerId), ModelResponseModel.class);

    makerResponseModel.setModels(models);

    return handleRequestSuccess(makerResponseModel, REQUEST_SUCCESS);
  }

  @ApiOperation(value = "Creates new maker entity.", response = MakerResponseModel.class)
  @PostMapping
  // TODO add description for parameters
  // TODO to implement method
  public ResponseEntity<?> createMaker(@Valid @RequestBody MakerCreateRequestModel createRequest) {

    return ResponseEntity.ok().build();
  }

  // TODO add description for parameters
  @ApiOperation(
      value = "Creates new model for the given maker entity.",
      response = ModelResponseModel.class)
  @PostMapping(value = "/{makerId}/models")
  public ResponseEntity<?> addModel(
      @PathVariable Long makerId, @Valid @RequestBody ModelCreateRequestModel createRequest) {

    MakerServiceModel updatedMaker =
        makerService.addModelToMaker(
            makerId, modelMapper.map(createRequest, ModelServiceModel.class));

    MakerResponseModel response = modelMapper.map(updatedMaker, MakerResponseModel.class);

    return handleCreateSuccess(
        response,
        String.format(MODEL_CREATE_SUCCESS, createRequest.getName(), updatedMaker.getName()),
        "/api/makers");
  }
}
