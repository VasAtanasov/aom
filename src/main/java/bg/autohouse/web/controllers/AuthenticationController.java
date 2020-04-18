package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.errors.UserRegistrationDisabledException;
import bg.autohouse.errors.UsernamePasswordLoginFailedException;
import bg.autohouse.security.jwt.AuthorizationHeader;
import bg.autohouse.security.jwt.JwtTokenService;
import bg.autohouse.security.jwt.JwtTokenType;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.user.AuthorizedUserServiceModel;
import bg.autohouse.service.services.AsyncUserLogger;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.DebugProfileUtil;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.OperationStatus;
import bg.autohouse.web.enums.RequestOperationName;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.LoginOrRegisterRequest;
import bg.autohouse.web.models.request.PasswordResetRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.UserRegisterRequest;
import bg.autohouse.web.models.response.OperationStatusResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  private final AsyncUserLogger userLogger;
  private final JwtTokenService jwtService;

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
    userLogger.logUserLogin(serviceModel.getUserId());
    log.info("Memory stats at present: {}", DebugProfileUtil.memoryStats());
    return RestUtil.okayResponseWithData(RestMessage.LOGIN_SUCCESSFUL, response);
  }

  @GetMapping(
      value = WebConfiguration.URL_USER_LOGOUT,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> logout(HttpServletRequest request) {
    AuthorizationHeader authHeader = new AuthorizationHeader(request);
    String token = authHeader.hasBearerToken() ? authHeader.getBearerToken() : null;
    if (token == null) return RestUtil.errorResponse(RestMessage.INVALID_TOKEN);
    jwtService.blackListJwt(token);
    return ResponseEntity.ok().build();
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
    return RestUtil.okayResponseWithData(
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
  public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequest resetRequest) {

    if (!ifExists(resetRequest.getUsername())) {
      log.info("Invalid user of passed username: {}", resetRequest.getUsername());
      return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.INVALID_USERNAME);
    }

    log.info("Creating a verifier for password reset with email ={}", resetRequest.getUsername());

    String token = userService.regenerateUserVerifier(resetRequest.getUsername());
    log.info("Sending verification email to: {} with value: {}", resetRequest.getUsername(), token);
    // TODO send to email
    return RestUtil.messageOkayResponse(RestMessage.PASSWORD_RESET_VERIFICATION_TOKEN_SENT);
  }

  @GetMapping(
      value = WebConfiguration.URL_PASSWORD_RESET_VALIDATE,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> validateOtp(
      @RequestParam("username") String username, @RequestParam(value = "code") String code) {

    boolean isValidOtp = passwordService.isShortLivedOtpValid(username, code);

    if (isValidOtp) {
      return RestUtil.messageOkayResponse(RestMessage.OTP_VALID);
    }

    return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.OTP_INVALID);
  }

  @GetMapping(
      value = WebConfiguration.URL_PASSWORD_RESET_COMPLETE,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> resetPassword(
      @RequestParam("username") String username,
      @RequestParam("password") String newPassword,
      @RequestParam("code") String code) {

    boolean isVerified = passwordService.isShortLivedOtpValid(username, code);

    if (!isVerified) {
      log.info("Token verification for password reset failed");
      return RestUtil.errorResponse(HttpStatus.UNAUTHORIZED, RestMessage.OTP_INVALID);
    }

    log.info("User code verified, now resetting user password");
    passwordService.resetPassword(username, code, newPassword);
    return RestUtil.messageOkayResponse(RestMessage.PASSWORD_RESET_SUCCESSFUL);
  }

  @GetMapping(
      value = WebConfiguration.URL_TOKEN_VALIDATE,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<ResponseWrapper> validateToken(@RequestParam("token") String token) {
    boolean isJwtTokenValid = jwtService.isJwtTokenValid(token);
    boolean isNotBlacklisted = !jwtService.isBlackListed(token);
    if (isJwtTokenValid && isNotBlacklisted)
      return RestUtil.messageOkayResponse(RestMessage.TOKEN_STILL_VALID);
    return RestUtil.errorResponse(HttpStatus.EXPECTATION_FAILED, RestMessage.INVALID_TOKEN);
  }

  @GetMapping(
      value = WebConfiguration.URL_TOKEN_REFRESH,
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<ResponseWrapper> refreshToken(@RequestParam("oldToken") String oldToken) {
    String newToken = jwtService.refreshToken(oldToken, JwtTokenType.API_CLIENT);
    if (newToken != null) {
      return RestUtil.okayResponseWithData(RestMessage.LOGIN_SUCCESSFUL, newToken);
    } else {
      return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.TOKEN_EXPIRED);
    }
  }

  private boolean ifExists(String username) {
    return userService.userExist(username);
  }

  @ExceptionHandler(NoSuchUserException.class)
  public ResponseEntity<?> noSuchUserResponse() {
    return RestUtil.errorResponse(RestMessage.INVALID_USERNAME);
  }

  @ExceptionHandler(UserRegistrationDisabledException.class)
  public ResponseEntity<?> userDisabledException() {
    return RestUtil.errorResponse(RestMessage.USER_REGISTRATION_DISABLED);
  }

  @ExceptionHandler(UsernamePasswordLoginFailedException.class)
  public ResponseEntity<?> loginFailedResponse() {
    return RestUtil.errorResponse(RestMessage.LOGIN_FAILED);
  }
}
