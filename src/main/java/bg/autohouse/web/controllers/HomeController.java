package bg.autohouse.web.controllers;

import static bg.autohouse.common.Constants.*;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.models.InitialStateModel;
import bg.autohouse.service.services.InitialStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_INDEX)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HomeController extends BaseController {

  private final InitialStateService initialStateService;

  @GetMapping(produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getInitialState() {

    InitialStateModel initialState = initialStateService.getInitialState();

    return handleRequestSuccess(initialState, REQUEST_SUCCESS);
  }
}
