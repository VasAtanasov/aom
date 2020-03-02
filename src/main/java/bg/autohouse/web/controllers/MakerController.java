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
import bg.autohouse.web.models.response.ModelResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(tags = "makers")
@RestController
@RequestMapping(WebConfiguration.URL_MAKERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MakerController extends BaseController {

  private final ModelMapperWrapper modelMapper;
  private final MakerService makerService;

  @ApiOperation(
      value = "Retrieves all makers with models available in database.",
      response = List.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping
  public ResponseEntity<?> getMakers() {

    List<MakerResponseModel> makers =
        makerService.getAllMakers().stream()
            .map(model -> modelMapper.map(model, MakerResponseModel.class))
            .collect(Collectors.toUnmodifiableList());

    // TODO add Pageable
    return handleSuccessRequest(makers, REQUEST_SUCCESS_);
  }

  @ApiOperation(
      value = "Retrieves a maker with all its models by the given ID.",
      response = MakerResponseModel.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 404, message = Constants.EXCEPTION_MAKER_NOT_FOUND),
        @ApiResponse(code = 200, message = "OK")
      })
  @GetMapping("{makerId}")
  public ResponseEntity<?> getMakerById(
      @ApiParam(name = "makerId", value = "The ID of the maker.", required = true)
          @PathVariable
          @NotNull
          Long makerId) {

    MakerResponseModel maker =
        modelMapper.map(makerService.getModelsForMaker(makerId), MakerResponseModel.class);

    return handleSuccessRequest(maker, REQUEST_SUCCESS_);
  }

  @ApiOperation(value = "Creates new maker entity.", response = MakerResponseModel.class)
  @PostMapping
  // TODO add description for parameters
  public ResponseEntity<?> createMaker(
      @Valid @RequestBody MakerCreateRequestModel createRequest, BindingResult result) {

    if (result.hasErrors()) return handleBadRequest(result, BAD_REQUEST);

    return ResponseEntity.ok().build();
  }

  @ApiOperation(
      value = "Creates new model for the given maker entity.",
      response = ModelResponseModel.class)
  @PostMapping("{makerId}")
  // TODO add description for parameters
  public ResponseEntity<?> addModel(
      @PathVariable @NotNull Long makerId,
      @Valid @RequestBody ModelCreateRequestModel createRequest,
      BindingResult result) {

    if (result.hasErrors()) return handleBadRequest(result, BAD_REQUEST);

    MakerServiceModel updatedMaker =
        makerService.addModelToMaker(
            makerId, modelMapper.map(createRequest, ModelServiceModel.class));

    MakerResponseModel response = modelMapper.map(updatedMaker, MakerResponseModel.class);
    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/makers")
            .buildAndExpand()
            .toUri();
    // TODO add handelCreateSuccess

    return handleSuccessRequest(
        response,
        String.format(MODEL_CREATE_SUCCESS, createRequest.getName(), updatedMaker.getName()));
  }
}
