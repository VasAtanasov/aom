package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.audit.models.UserLogoutEvent;
import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.security.jwt.AuthorizationHeader;
import bg.autohouse.security.jwt.JwtTokenService;
import bg.autohouse.security.jwt.JwtTokenType;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.user.AuthorizedUserServiceModel;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.DebugProfileUtil;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.ActionStatus;
import bg.autohouse.web.enums.ActionType;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.LoginOrRegisterRequest;
import bg.autohouse.web.models.request.PasswordResetRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.UserRegisterRequest;
import bg.autohouse.web.models.response.ActionStatusResponse;
import bg.autohouse.web.models.response.ResponseWrapper;
import bg.autohouse.web.models.response.user.AuthorizedUserResponseModel;
import bg.autohouse.web.util.RestUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
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
@RequestMapping(WebConfiguration.URL_USER_AUTH)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationController extends BaseController {

  private final UserService userService;
  private final PasswordService passwordService;
  private final ModelMapperWrapper modelMapper;
  private final JwtTokenService jwtService;
  private final ApplicationEventPublisher eventPublisher;

  @PostMapping(
      value = WebConfiguration.URL_USER_LOGIN,
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> login(
      @Valid @RequestBody UserLoginRequest loginRequest, HttpServletResponse res)
      throws IOException {
    long startTime = System.currentTimeMillis();
    AuthorizedUserServiceModel serviceModel =
        userService.tryLogin(loginRequest.getUsername(), loginRequest.getPassword());
    AuthorizedUserResponseModel response =
        modelMapper.map(serviceModel, AuthorizedUserResponseModel.class);
    res.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + serviceModel.getToken());
    res.addHeader("UserID", serviceModel.getUserId());
    log.info(
        "User login took {} s, now recording use",
        DebugProfileUtil.elapsedTimeInSeconds(startTime));
    log.info("Memory stats at present: {}", DebugProfileUtil.memoryStats());
    return RestUtil.okResponse(RestMessage.USER_LOGIN_SUCCESSFUL, response);
  }

  @GetMapping(
      value = WebConfiguration.URL_USER_LOGOUT,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> logout(HttpServletRequest request, @LoggedUser User user) {
    AuthorizationHeader authHeader = new AuthorizationHeader(request);
    String token = authHeader.hasBearerToken() ? authHeader.getBearerToken() : null;
    jwtService.blackListJwt(token);
    eventPublisher.publishEvent(UserLogoutEvent.of(user.getId()));
    return ResponseEntity.ok().build();
  }

  @PostMapping(
      value = WebConfiguration.URL_USER_LOGIN_OR_REGISTER,
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> loginOrRegister(@Valid @RequestBody LoginOrRegisterRequest request) {
    boolean exists = ifExists(request.getUsername());
    ActionStatusResponse action =
        ActionStatusResponse.builder()
            .type(ActionType.LOGIN_OR_REGISTER)
            .status(exists ? ActionStatus.LOGIN : ActionStatus.REGISTER)
            .build();
    ResponseWrapper response = RestUtil.wrapper(b -> b.action(action));
    return ResponseEntity.ok(response);
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
      return RestUtil.errResponse(HttpStatus.CONFLICT, RestMessage.USER_ALREADY_EXISTS);
    }
    log.info("Creating a verifier for a new user with email ={}", model.getUsername());
    String token = userService.generateUserRegistrationVerifier(model);
    log.info("Sending verification email to: {} with value: {}", model.getUsername(), token);
    // TODO send to email
    return RestUtil.okResponse(
        RestMessage.REGISTRATION_VERIFICATION_TOKEN_SENT, toMap("code", token));
  }

  @GetMapping(
      value = WebConfiguration.URL_USER_REGISTER_EMAIL_VERIFICATION,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> verifyRegistration(
      @RequestParam(value = "username") String username,
      @RequestParam(value = "code") String code) {
    boolean isVerified = passwordService.isShortLivedOtpValid(username, code);
    if (isVerified) {
      log.info("User code verified, now creating user with email={}", username);
      UserServiceModel user = userService.completeRegistration(username);
      passwordService.expireVerificationCode(user.getId());
      return RestUtil.okResponse(RestMessage.USER_REGISTRATION_VERIFIED);
    } else {
      log.info("Token verification for new user failed");
      return RestUtil.errResponse(HttpStatus.UNAUTHORIZED, RestMessage.INVALID_REGISTRATION_TOKEN);
    }
  }

  @PostMapping(
      value = WebConfiguration.URL_PASSWORD_RESET_REQUEST,
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequest resetRequest) {
    if (!ifExists(resetRequest.getUsername())) {
      log.info("Invalid user of passed username: {}", resetRequest.getUsername());
      return RestUtil.errResponse(HttpStatus.BAD_REQUEST, RestMessage.INVALID_USERNAME);
    }
    log.info("Creating a verifier for password reset with email ={}", resetRequest.getUsername());
    String token = userService.regenerateUserVerifier(resetRequest.getUsername());
    log.info("Sending verification email to: {} with value: {}", resetRequest.getUsername(), token);
    // TODO send to email
    return RestUtil.okResponse(RestMessage.PASSWORD_RESET_VERIFICATION_TOKEN_SENT);
  }

  @GetMapping(
      value = WebConfiguration.URL_PASSWORD_RESET_VALIDATE,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> validateOtp(
      @RequestParam("username") String username, @RequestParam(value = "code") String code) {
    boolean isValidOtp = passwordService.isShortLivedOtpValid(username, code);
    if (isValidOtp) {
      return RestUtil.okResponse(RestMessage.OTP_VALID);
    }
    return RestUtil.errResponse(HttpStatus.BAD_REQUEST, RestMessage.OTP_INVALID);
  }

  @GetMapping(
      value = WebConfiguration.URL_PASSWORD_RESET_COMPLETE,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> resetPassword(
      @RequestParam("username") String username,
      @RequestParam("password") String newPassword,
      @RequestParam("code") String code) {
    if (!ifExists(username)) {
      log.info("Invalid user of passed username: {}", username);
      return RestUtil.errResponse(HttpStatus.BAD_REQUEST, RestMessage.INVALID_USERNAME);
    }
    boolean isVerified = passwordService.isShortLivedOtpValid(username, code);
    if (!isVerified) {
      log.info("Token verification for password reset failed");
      return RestUtil.errResponse(HttpStatus.UNAUTHORIZED, RestMessage.OTP_INVALID);
    }
    log.info("User code verified, now resetting user password");
    passwordService.resetPassword(username, code, newPassword);
    return RestUtil.okResponse(RestMessage.PASSWORD_RESET_SUCCESSFUL);
  }

  @GetMapping(
      value = WebConfiguration.URL_TOKEN_VALIDATE,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<ResponseWrapper> validateToken(@RequestParam("token") String token) {
    if (jwtService.isJwtTokenValid(token) && !jwtService.isBlackListed(token)) {
      return RestUtil.okResponse(RestMessage.TOKEN_STILL_VALID);
    }
    return RestUtil.errResponse(HttpStatus.EXPECTATION_FAILED, RestMessage.INVALID_TOKEN);
  }

  @GetMapping(
      value = WebConfiguration.URL_TOKEN_REFRESH,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<ResponseWrapper> refreshToken(@RequestParam("oldToken") String oldToken) {
    String newToken = jwtService.refreshToken(oldToken, JwtTokenType.API_CLIENT);
    if (newToken != null) {
      return RestUtil.okResponse(RestMessage.USER_LOGIN_SUCCESSFUL, newToken);
    } else {
      return RestUtil.errResponse(HttpStatus.BAD_REQUEST, RestMessage.TOKEN_EXPIRED);
    }
  }

  private boolean ifExists(String username) {
    return userService.userExist(username);
  }
}
