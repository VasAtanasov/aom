package bg.autohouse.web.controllers;

import static bg.autohouse.common.Constants.*;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.enums.Textable;
import bg.autohouse.util.ClassFinder;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.Payload;
import java.util.HashMap;
import java.util.Map;
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
  public ResponseEntity<?> getInitialState() {

    // TODO group by type of criteria (Select or multichouse)
    Map<String, Object> criteria = new HashMap<>();
    ClassFinder.find("bg.autohouse.data.models.enums").stream()
        .filter(cls -> cls.isEnum() && Textable.class.isAssignableFrom(cls))
        .forEach(
            cls -> criteria.put(lowerFirstLetter(cls.getSimpleName()), EnumUtils.ENUM_MAP(cls)));

    Map<String, Object> payload = Payload.builder().field("taxonomy", criteria).build();

    return handleRequestSuccess(payload, REQUEST_SUCCESS);
  }

  private String lowerFirstLetter(String word) {
    return word.substring(0, 1).toLowerCase() + word.substring(1);
  }
}
