package bg.autohouse.web.controllers;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.account.DealerAccountCreateUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/location.sql")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTest extends MvcPerformer {

  private static final String API_BASE = "/api/vehicles/offers";
  private static final UserLoginRequest LOGIN_REQUEST_ROOT =
      UserLoginRequest.of(DatabaseSeeder.ROOT_USERNAME, "123");

  private static final List<String> usernames =
      List.of(
          "ernestina_altenwerth@gmail.com",
          "jordy_beatty20@yahoo.com",
          "noe36@yahoo.com",
          "romaine_terry40@gmail.com",
          "bethel91@gmail.com");

  @Autowired protected MockMvc mockMvc;
  @Autowired private UserService userService;
  @Autowired private AccountService accountService;
  @Autowired ObjectMapper mapper;
  private HttpHeaders headers;

  DealerAccountCreateUpdateRequest accountCreateRequest;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @BeforeEach
  void initHeaders() throws Exception {
    if (headers == null) headers = getAuthHeadersFor(LOGIN_REQUEST_ROOT);
    String content = JsonReaderUtil.readJsonContent("dealer-account.json");
    mapper.readValue(content, DealerAccountCreateUpdateRequest.class);
  }
}
