package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.models.InitialStateModel;
import bg.autohouse.service.services.InitialStateService;
import bg.autohouse.service.services.ProvinceService;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.response.ResponseWrapper;
import bg.autohouse.web.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_INDEX)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HomeController extends BaseController {

  private final InitialStateService initialStateService;
  private final ProvinceService provinceService;

  @GetMapping(
      value = "/state",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<ResponseWrapper> getInitialState() {
    InitialStateModel initialState = initialStateService.getInitialState();
    return RestUtil.okResponse(RestMessage.INITIAL_STATE_GET_SUCCESSFUL, initialState);
  }

  @GetMapping(
      value = "/province/list",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getProvinceList() {
    return RestUtil.okResponse(provinceService.loadAllProvinces());
  }

  @GetMapping(
      value = "/province/{id}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getProvinceById(@PathVariable Long id) {
    return RestUtil.okResponse(provinceService.loadProvinceById(id));
  }
}
