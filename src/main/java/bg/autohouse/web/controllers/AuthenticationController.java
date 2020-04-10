package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.security.jwt.JwtTokenProvider;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.OperationStatus;
import bg.autohouse.web.enums.RequestOperationName;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.LoginOrRegisterRequest;
import bg.autohouse.web.models.request.PasswordResetRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.UserRegisterRequest;
import bg.autohouse.web.models.response.OperationStatusResponse;
import bg.autohouse.web.util.RestUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(WebConfiguration.URL_USER_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationController extends BaseController {

  private final UserService userService;
  private final PasswordService passwordService;
  private final ModelMapperWrapper modelMapper;
  private final JwtTokenProvider tokenProvider;

  @PostMapping(WebConfiguration.URL_USER_LOGIN)
  public void theFakeLogin(@RequestBody UserLoginRequest loginRequest) {
    throw new IllegalStateException(
        "This method should not be called. This method is implemented by Spring Security");
  }

  @PostMapping(
      value = WebConfiguration.URL_USER_LOGIN_OR_REGISTER,
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> loginOrRegister(@Valid @RequestBody LoginOrRegisterRequest request) {
    boolean exists = ifExists(request.getUsername());

    return RestUtil.okayResponseWithData(
        OperationStatusResponse.builder()
            .operation(RequestOperationName.LOGIN_OR_REGISTER.name())
            .result(exists ? OperationStatus.LOGIN.name() : OperationStatus.REGISTER.name())
            .build());
  }

  @PostMapping(
      value = WebConfiguration.URL_USER_REGISTER,
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request) {
    UserRegisterServiceModel model = modelMapper.map(request, UserRegisterServiceModel.class);

    if (ifExists(model.getUsername())) {
      log.info(
          "Creating a verifier for user with email ={}, user already exists.", model.getUsername());
      return RestUtil.errorResponse(HttpStatus.CONFLICT, RestMessage.USER_ALREADY_EXISTS);
    }

    log.info("Creating a verifier for a new user with email ={}", model.getUsername());

    String token = userService.generateUserRegistrationVerifier(model);
    log.info("Sending verification email to: {} with value: {}", model.getUsername(), token);
    // TODO send to email
    return RestUtil.messageOkayResponse(RestMessage.REGISTRATION_VERIFICATION_TOKEN_SENT);
  }

  @GetMapping(
      value = WebConfiguration.URL_USER_REGISTER_EMAIL_VERIFICATION,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> verifyRegistration(@RequestParam(value = "token") String token) {

    boolean isVerified = passwordService.verifyEmailToken(token);

    if (isVerified) {
      String username = tokenProvider.getUsernameFromJWT(token);
      log.info("User code verified, now creating user with email={}", username);
      UserServiceModel user = userService.completeRegistration(username);
      passwordService.invalidateRegistrationToken(user.getUsername());
      return RestUtil.messageOkayResponse(RestMessage.USER_REGISTRATION_VERIFIED);
    } else {
      log.info("Token verification for new user failed");
      return RestUtil.errorResponse(
          HttpStatus.UNAUTHORIZED, RestMessage.INVALID_REGISTRATION_TOKEN);
    }
  }

  @PostMapping(
      value = WebConfiguration.URL_PASSWORD_RESET_REQUEST,
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> requestReset(@RequestBody PasswordResetRequest resetRequest) {

    if (!ifExists(resetRequest.getUsername())) {
      log.info("Invalid user of passed username: {}", resetRequest.getUsername());
      return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.INVALID_USERNAME);
    }

    log.info("Creating a verifier for password reset with email ={}", resetRequest.getUsername());

    String token = userService.generatePasswordResetVerifier(resetRequest.getUsername());
    log.info("Sending verification email to: {} with value: {}", resetRequest.getUsername(), token);
    // TODO send to email
    return RestUtil.messageOkayResponse(RestMessage.PASSWORD_RESET_VERIFICATION_TOKEN_SENT);
  }

  private boolean ifExists(String username) {
    return userService.userExist(username);
  }
}
