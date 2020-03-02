package bg.autohouse.web.controllers;

import bg.autohouse.common.Constants;
import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.services.MakerService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.MakerCreateRequestModel;
import bg.autohouse.web.models.response.MakerResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "makers")
@RestController
@RequestMapping(WebConfiguration.URL_MAKERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MakerController {

  private final ModelMapperWrapper modelMapper;
  private final MakerService makerService;

  @ApiOperation(
      value = "Retrieves all makers with models available in database.",
      response = List.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping
  public ResponseEntity<List<MakerResponseModel>> getMakers() {

    List<MakerResponseModel> makers =
        makerService.getAllMakers().stream()
            .map(model -> modelMapper.map(model, MakerResponseModel.class))
            .collect(Collectors.toUnmodifiableList());

    return new ResponseEntity<>(makers, HttpStatus.OK);
  }

  @ApiOperation("Retrieves a maker with all its models by the given ID.")
  @ApiResponses(
      value = {
        @ApiResponse(code = 404, message = Constants.EXCEPTION_MAKER_NOT_FOUND),
        @ApiResponse(code = 200, message = "OK")
      })
  @GetMapping("{makerId}")
  public ResponseEntity<MakerResponseModel> getMakerById(
      @PathVariable
          @NotNull
          @ApiParam(name = "makerId", value = "The ID of the maker.", required = true)
          Long makerId) {

    MakerResponseModel maker =
        modelMapper.map(makerService.getModelsForMaker(makerId), MakerResponseModel.class);

    return new ResponseEntity<>(maker, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<MakerResponseModel> createMaker(
      @Valid @RequestBody MakerCreateRequestModel createRequest, BindingResult result) {

    return null;
  }
}
