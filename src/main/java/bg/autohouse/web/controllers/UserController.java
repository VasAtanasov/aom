package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.UserRegisterRequest;
import bg.autohouse.web.util.RestUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_USER_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController extends BaseController {

  private final UserService userService;
  private final ModelMapperWrapper modelMapper;

  @PostMapping(WebConfiguration.URL_USER_LOGIN)
  public void theFakeLogin(@RequestBody UserLoginRequest loginRequest) {
    throw new IllegalStateException(
        "This method should not be called. This method is implemented by Spring Security");
  }

  @PostMapping(
      value = WebConfiguration.URL_USER_REGISTER,
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest model) {
    UserRegisterServiceModel registerServiceModel =
        modelMapper.map(model, UserRegisterServiceModel.class);
    UserServiceModel serviceModel = userService.register(registerServiceModel);
    String locationUrl =
        WebConfiguration.URL_API_BASE
            + WebConfiguration.URL_USER_BASE
            + WebConfiguration.URL_USER_REGISTER;
    return RestUtil.createSuccessResponse(
        serviceModel, RestMessage.USER_REGISTRATION_SUCCESSFUL, locationUrl);
  }
}
