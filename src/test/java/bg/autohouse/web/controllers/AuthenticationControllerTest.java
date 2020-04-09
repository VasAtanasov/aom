package bg.autohouse.web.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.web.models.request.LoginOrRegisterRequest;
import bg.autohouse.web.models.request.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/users";

  private static final UserRegisterRequest VALID_USER_REGISTER_MODEL =
      UserRegisterRequest.builder()
          .username("username@mail.com")
          .password("password")
          .confirmPassword("password")
          .build();

  @Autowired protected MockMvc mockMvc;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @Test
  void when_loginOrRegister_withNonExistingUsername_thenReturns200() throws Exception {
    LoginOrRegisterRequest request = LoginOrRegisterRequest.of("username@mail.com");

    performPost(API_BASE + "/register/login-or-register", request).andExpect(status().isOk());
    // .andExpect(jsonPath("$.operationName", is(OperationStatus.REGISTER.name())))
    // .andExpect(
    //     jsonPath("$.operationResult", is(RequestOperationName.LOGIN_OR_REGISTER.name())));
  }
}
