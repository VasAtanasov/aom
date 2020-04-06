package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.models.InitialStateModel;
import bg.autohouse.service.services.InitialStateService;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.response.ResponseWrapper;
import bg.autohouse.web.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_VEHICLES)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HomeController extends BaseController {

  private final InitialStateService initialStateService;

  @GetMapping(
      value = "/state",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<ResponseWrapper> getInitialState() {
    InitialStateModel initialState = initialStateService.getInitialState();
    return RestUtil.okayResponseWithData(RestMessage.INITIAL_STATE_GET_SUCCESSFUL, initialState);
  }
}
