package bg.autohouse.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.services.UserService;
import bg.autohouse.utils.TimingExtension;
import bg.autohouse.web.enums.OperationStatus;
import bg.autohouse.web.enums.RequestOperationName;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.LoginOrRegisterRequest;
import bg.autohouse.web.models.request.PasswordResetRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(TimingExtension.class)
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/auth";
  private static final UserRegisterServiceModel VALID_REGISTER_MODEL =
      UserRegisterServiceModel.builder().username("username@mail.com").password("password").build();
  private static final UserRegisterRequest VALID_USER_REGISTER_MODEL =
      UserRegisterRequest.builder()
          .username("username@mail.com")
          .password("password")
          .confirmPassword("password")
          .build();

  @Autowired protected MockMvc mockMvc;
  @Autowired private UserService userService;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }
  // TODO test login of disabled user
  @Test
  void when_loginOrRegister_withNonExistingUsername_thenReturns200() throws Exception {
    LoginOrRegisterRequest request = LoginOrRegisterRequest.of("username@mail.com");

    performPost(API_BASE + "/login-or-register", request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.operation", is(RequestOperationName.LOGIN_OR_REGISTER.name())))
        .andExpect(jsonPath("$.data.result", is(OperationStatus.REGISTER.name())));
  }

  @Test
  void when_loginOrRegister_withExistingUsername_thenReturns200() throws Exception {
    userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);
    userService.completeRegistration(VALID_REGISTER_MODEL.getUsername());
    LoginOrRegisterRequest request = LoginOrRegisterRequest.of("username@mail.com");

    performPost(API_BASE + "/login-or-register", request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.operation", is(RequestOperationName.LOGIN_OR_REGISTER.name())))
        .andExpect(jsonPath("$.data.result", is(OperationStatus.LOGIN.name())));
  }

  @Test
  void when_register_withValidModel_thenReturns200() throws Exception {
    performPost(API_BASE + "/register", VALID_USER_REGISTER_MODEL)
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.message", is(RestMessage.REGISTRATION_VERIFICATION_TOKEN_SENT.name())));
  }

  @Test
  void when_register_withExistingUser_thenReturns409() throws Exception {
    userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);
    userService.completeRegistration(VALID_REGISTER_MODEL.getUsername());

    performPost(API_BASE + "/register", VALID_USER_REGISTER_MODEL)
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message", is(RestMessage.USER_ALREADY_EXISTS.name())));
  }

  @Test
  void when_login_withValidUser_thenReturn200() throws Exception {
    UserLoginRequest loginRequest = UserLoginRequest.of(DatabaseSeeder.USERNAME, "123");
    MvcResult mvcResult = performPost(API_BASE + "/login", loginRequest).andReturn();
    String authHeader = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    assertThat(authHeader).isNotNull();
  }

  @Test
  void when_verifyRegistration_withValidToken_thenReturns200() throws Exception {
    String code = userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);

    performGet(
            API_BASE
                + "/register/verify?code="
                + code
                + "&username="
                + VALID_USER_REGISTER_MODEL.getUsername())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(RestMessage.USER_REGISTRATION_VERIFIED.name())));
  }

  @Test
  void when_register_withInvalidBody_thenReturns400() throws Exception {
    UserRegisterRequest invalidModel =
        UserRegisterRequest.builder()
            .username(null)
            .password("password")
            .confirmPassword("password")
            .build();

    performPost(API_BASE + "/register", invalidModel).andExpect(status().isBadRequest());
  }

  @Test
  void when_verifyRegistration_withInvalidToke_thenReturns401() throws Exception {
    performGet(
            API_BASE
                + "/register/verify?code="
                + "invalid_code"
                + "&username="
                + VALID_USER_REGISTER_MODEL.getUsername())
        .andExpect(status().isUnauthorized());
  }

  @Test
  void when_requestPasswordReset_validUsername_shouldReturn200() throws Exception {
    userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);
    userService.completeRegistration(VALID_REGISTER_MODEL.getUsername());
    PasswordResetRequest resetRequest = PasswordResetRequest.of(VALID_REGISTER_MODEL.getUsername());

    performPost(API_BASE + "/password-reset-request", resetRequest)
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.message", is(RestMessage.PASSWORD_RESET_VERIFICATION_TOKEN_SENT.name())));
  }

  @Test
  void when_resetPassword_validToken_shouldReturn200() throws Exception {
    String code = userService.regenerateUserVerifier(DatabaseSeeder.USERNAME);

    performGet(
            API_BASE
                + "/password-reset-complete?username="
                + DatabaseSeeder.USERNAME
                + "&code="
                + code
                + "&password=12345")
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(RestMessage.PASSWORD_RESET_SUCCESSFUL.name())));
  }

  @Test
  void when_validateOtp_validToken_shouldReturn200() throws Exception {
    String code = userService.regenerateUserVerifier(DatabaseSeeder.USERNAME);

    performGet(
            API_BASE
                + "/reset-password-validate?username="
                + DatabaseSeeder.USERNAME
                + "&code="
                + code)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(RestMessage.OTP_VALID.name())));
  }

  @Test
  void when_validateOtp_invalidToken_shouldReturn400() throws Exception {
    performGet(
            API_BASE
                + "/reset-password-validate?username="
                + DatabaseSeeder.USERNAME
                + "&code="
                + "invalid_code")
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.OTP_INVALID.name())));
  }

  @Test
  void when_validateToken_invalidToken_shouldReturn400() throws Exception {
    performGet(API_BASE + "/token/validate?token=" + "invalid_code")
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.INVALID_TOKEN.name())));
  }
}
