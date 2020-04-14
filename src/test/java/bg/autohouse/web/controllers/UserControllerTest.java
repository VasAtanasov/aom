package bg.autohouse.web.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.utils.WithAutohouseUser;
import bg.autohouse.web.models.request.account.PrivateSellerAccountCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/users";

  @Autowired protected MockMvc mockMvc;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @Test
  @WithAutohouseUser(DatabaseSeeder.USERNAME)
  void when_createPrivateSellerAccount() throws Exception {
    PrivateSellerAccountCreateRequest request = new PrivateSellerAccountCreateRequest();
    request.setFirstName("firstName");
    request.setLastName("lastName");

    performPost(API_BASE + "/account/private", request).andExpect(status().isOk());
  }
}
