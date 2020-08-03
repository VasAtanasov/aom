package bg.autohouse.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.service.models.user.UserRowServiceModel;
import bg.autohouse.utils.RestResponsePage;
import bg.autohouse.web.models.request.UserLoginRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/admin";
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
    ResultActions resultActions =
        performGet(API_BASE + "/users/list", headers).andExpect(status().isOk());
    MvcResult result = resultActions.andReturn();
    ObjectMapper mapper = new ObjectMapper();
    String contentAsString = result.getResponse().getContentAsString();
    RestResponsePage<UserRowServiceModel> page =
        mapper.readValue(
            contentAsString, new TypeReference<RestResponsePage<UserRowServiceModel>>() {});
    assertThat(page).isNotNull();
    assertThat(page.getContent()).isNotEmpty();
  }
}
