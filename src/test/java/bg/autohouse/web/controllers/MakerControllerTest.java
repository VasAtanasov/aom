package bg.autohouse.web.controllers;

import static bg.autohouse.web.controllers.ResponseBodyMatchers.responseBody;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.MakerCreateRequestModel;
import bg.autohouse.web.models.request.ModelCreateRequestModel;
import bg.autohouse.web.models.request.UserLoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Sql("/makersModelsTrims.sql")
@TestPropertySource("classpath:test.properties")
public class MakerControllerTest extends MvcPerformer {
  static final String API_BASE = "/api/vehicles";
  private static final UserLoginRequest LOGIN_REQUEST_ROOT =
      UserLoginRequest.of(DatabaseSeeder.ROOT_USERNAME, "123");

  @Autowired private MockMvc mockMvc;

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
  void whenGetMakers_thenReturns200() throws Exception {

    performGet(API_BASE + "/makers")
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(RestMessage.MAKERS_GET_SUCCESSFUL.name())))
        .andReturn();
  }

  @Test
  void whenGetMaker_withValidId_thenReturns200() throws Exception {
    Long makerId = 1L;

    performGet(API_BASE + "/makers/" + makerId)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is(RestMessage.MAKER_GET_SUCCESSFUL.name())))
        .andExpect(jsonPath("$.data.maker.id", is(1)));
  }

  @Test
  void whenInvalidId_thenReturns404() throws Exception {

    performGet(API_BASE + "/makers/" + 1290L)
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is(RestMessage.MAKER_NOT_FOUND.name())));
  }

  @Test
  void whenInvalidMediaType_thenReturns406() throws Exception {

    mockMvc
        .perform(get(API_BASE + "/makers/").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotAcceptable());
  }

  @Test
  public void whenCreateModel_withValidBody_shouldReturn201() throws Exception {

    ModelCreateRequestModel createRequestModel = ModelCreateRequestModel.of("New Model", 1L);
    performPost(API_BASE + "/makers/1", createRequestModel, headers)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message", is(RestMessage.MAKER_MODEL_CREATED.name())));
  }

  @Test
  public void whenCreateModel_withEmptyName_shouldReturn400() throws Exception {

    ModelCreateRequestModel createRequestModel = ModelCreateRequestModel.of("", 1L);

    performPost(API_BASE + "/makers/1", createRequestModel, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.PARAMETER_VALIDATION_FAILURE.name())))
        .andExpect(jsonPath("$.errors", hasSize(2)));
  }

  @Test
  public void whenCreateModel_withNullName_shouldReturn400() throws Exception {
    ModelCreateRequestModel createRequestModel = ModelCreateRequestModel.of(null, 1L);

    performPost(API_BASE + "/makers/1", createRequestModel, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.PARAMETER_VALIDATION_FAILURE.name())))
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(responseBody().containsError("name", ValidationMessages.MODEL_NAME_BLANK));
  }

  @Test
  public void whenCreateModel_withNullId_shouldReturn400() throws Exception {
    ModelCreateRequestModel createRequestModel = ModelCreateRequestModel.of("A4", null);

    performPost(API_BASE + "/makers/1", createRequestModel, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.PARAMETER_VALIDATION_FAILURE.name())))
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(responseBody().containsError("makerId", ValidationMessages.MAKER_ID_NULL));
  }

  @Test
  public void whenCreateModel_witInvalidTypeId_shouldReturn400() throws Exception {
    String createModelJson = "{\"name\":\"A4\",\"makerId\":\"invalid_id\"}";

    performPost(API_BASE + "/makers/1", createModelJson, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.SOMETHING_WENT_WRONG.name())));
  }

  @Test
  public void whenCreateMaker_withValidBody_shouldReturn201() throws Exception {
    final String MAKER_NAME = "New Maker";

    MakerCreateRequestModel createRequestModel = MakerCreateRequestModel.of(MAKER_NAME);
    performPost(API_BASE + "/makers", createRequestModel, headers)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message", is(RestMessage.MAKER_CREATED.name())))
        .andExpect(jsonPath("$.data.maker.name", is(MAKER_NAME)));
  }
}
