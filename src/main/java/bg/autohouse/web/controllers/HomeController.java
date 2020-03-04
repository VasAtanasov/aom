package bg.autohouse.web.controllers;

import static bg.autohouse.common.Constants.*;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.enums.OrderBy;
import bg.autohouse.util.EnumUtils;
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

  @GetMapping(produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getMakers() {

    return handleRequestSuccess(EnumUtils.ENUM_MAP(OrderBy.class), REQUEST_SUCCESS);
  }
}
