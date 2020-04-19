package bg.autohouse.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.UserChangePasswordRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/users";
  private static final UserLoginRequest LOGIN_REQUEST_ROOT =
      UserLoginRequest.of(DatabaseSeeder.ROOT_USERNAME, "123");

  @Autowired protected MockMvc mockMvc;
  private HttpHeaders headers;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @BeforeEach
  void initHeaders() throws Exception {
    if (headers == null) headers = getAuthHeadersFor(LOGIN_REQUEST_ROOT);
  }

  @Test
  void when_changePassword_thenReturn200() throws Exception {
    UserChangePasswordRequest request =
        UserChangePasswordRequest.builder()
            .oldPassword("123")
            .newPassword("1234")
            .confirmPassword("1234")
            .build();

    performPost(API_BASE + "/password/update", request, headers).andExpect(status().isOk());
  }

  @Test
  void when_changePassword_invalidOldPassword_thenReturn400() throws Exception {
    UserChangePasswordRequest request =
        UserChangePasswordRequest.builder()
            .oldPassword("1234")
            .newPassword("1234")
            .confirmPassword("1234")
            .build();

    performPost(API_BASE + "/password/update", request, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.INVALID_PASSWORD.name())));
  }

  @Test
  void when_changePassword_missingOldPassword_thenReturn400() throws Exception {
    UserChangePasswordRequest request =
        UserChangePasswordRequest.builder()
            .oldPassword(null)
            .newPassword("1234")
            .confirmPassword("1234")
            .build();

    performPost(API_BASE + "/password/update", request, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.PARAMETER_VALIDATION_FAILURE.name())));
  }
}
