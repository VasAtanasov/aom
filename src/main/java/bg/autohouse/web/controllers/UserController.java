package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.common.Constants;
import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.models.RegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.RegisterRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
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
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest model) {
    RegisterServiceModel registerServiceModel = modelMapper.map(model, RegisterServiceModel.class);
    UserServiceModel serviceModel = userService.register(registerServiceModel);
    return handleCreateSuccess(
        serviceModel,
        Constants.USER_CREATE_SUCCESS,
        WebConfiguration.URL_API_BASE
            + WebConfiguration.URL_USER_BASE
            + WebConfiguration.URL_USER_REGISTER);
  }
}
