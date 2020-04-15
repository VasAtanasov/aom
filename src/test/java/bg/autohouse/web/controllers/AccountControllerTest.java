package bg.autohouse.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.account.ContactDetailsModel;
import bg.autohouse.web.models.request.account.PrivateSellerAccountCreateRequest;
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
public class AccountControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/accounts";
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
  void when_createPrivateSellerAccount_thenReturn200() throws Exception {
    PrivateSellerAccountCreateRequest request =
        PrivateSellerAccountCreateRequest.builder()
            .firstName("firstName")
            .lastName("lastName")
            .displayedName("displayedName")
            .description("Bla bla bla")
            .contactDetails(
                ContactDetailsModel.builder().phoneNumber("phoneNumber").webLink("webLink").build())
            .build();

    performPost(API_BASE + "/private-create", request, headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(RestMessage.PRIVATE_SELLER_ACCOUNT_CREATED.name())));

    performPost(API_BASE + "/private-create", request, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.USER_ALREADY_HAS_ACCOUNT.name())));
  }
}
