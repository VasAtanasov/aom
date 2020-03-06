package bg.autohouse.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.BaseTest;
import bg.autohouse.common.Constants;
import bg.autohouse.data.models.Maker;
import bg.autohouse.data.models.Model;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.data.repositories.ModelRepository;
import bg.autohouse.util.JsonParser;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.ModelCreateRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class MakerControllerTest extends BaseTest {
  static final String MAKER_NAME = "Audi";

  @Autowired ObjectMapper objectMapper;

  @Autowired ModelMapperWrapper modelMapper;

  @Autowired MakerRepository makerRepository;

  @Autowired ModelRepository modelRepository;

  @Autowired JsonParser jsonParser;

  @Before
  public void setUp() {

    Maker maker = Maker.of(MAKER_NAME);
    makerRepository.save(maker);

    Model a4 = Model.of("A4", maker);
    Model a3 = Model.of("A3", maker);

    modelRepository.save(a4);
    modelRepository.save(a3);

    log.info(jsonParser.toString(maker));
  }

  @Test
  public void whenGetMakers_shouldReturn() throws Exception {
    mvcPerformer.performGet(API_BASE + "/makers").andExpect(status().isOk());
  }

  @Test
  public void whenGetMaker_existingId_shouldReturnOk() throws Exception {
    mvcPerformer
        .performGet(API_BASE + "/makers/1")
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.message", is(Constants.REQUEST_SUCCESS)))
        .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
        .andExpect(jsonPath("$.data.maker.name", is(MAKER_NAME)))
        .andReturn();
  }

  @Test
  public void whenGetMaker_notExistingId_shouldReturnFalse() throws Exception {
    mvcPerformer
        .performGet(API_BASE + "/makers/123")
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success", is(false)))
        .andExpect(jsonPath("$.message", is(Constants.EXCEPTION_MAKER_NOT_FOUND)))
        .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())));
  }

  @Test
  public void whenCreateModel_shouldReturnCreated() throws Exception {
    ModelCreateRequestModel model = ModelCreateRequestModel.of("Banana");

    String expectedMessage = String.format(Constants.MODEL_CREATE_SUCCESS, "Banana", MAKER_NAME);

    mvcPerformer
        .performPost(API_BASE + "/makers/1/models", model)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.message", is(expectedMessage)))
        .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())));
  }
}
