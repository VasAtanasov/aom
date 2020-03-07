package bg.autohouse.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.common.Constants;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.errors.MakerNotFoundException;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.models.ModelServiceModel;
import bg.autohouse.service.services.InitialStateService;
import bg.autohouse.service.services.MakerService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.ModelCreateRequestModel;
import bg.autohouse.web.models.response.MakerResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MakerController.class)
public class MakerControllerIsolationTest extends MvcPerformer {
  static final String MAKER_NAME = "Audi";
  static final String API_BASE = "/api/vehicles";

  @Autowired private MockMvc mockMvc;
  @MockBean private MakerRepository makerRepository;
  @MockBean private MakerService makerService;
  @MockBean private InitialStateService initialStateService;
  @MockBean private ModelMapperWrapper modelMapper;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @Test
  void whenValidId_thenReturns200() throws Exception {

    MakerServiceModel model = MakerServiceModel.builder().id(1L).name(MAKER_NAME).build();
    MakerResponseModel response = MakerResponseModel.builder().id(1L).name(MAKER_NAME).build();

    given(makerService.getOne(1L)).willReturn(model);
    given(modelMapper.map(model, MakerResponseModel.class)).willReturn(response);

    performGet(API_BASE + "/makers/" + model.getId())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
        .andExpect(jsonPath("$.data.maker.name", is(MAKER_NAME)))
        .andExpect(jsonPath("$.data.maker.id", is(1)));
  }

  @Test
  void whenInvalidId_thenReturns404() throws Exception {

    given(makerService.getOne(anyLong())).willThrow(new MakerNotFoundException());

    performGet(API_BASE + "/makers/" + 12L)
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success", is(false)))
        .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
        .andExpect(jsonPath("$.message", is(Constants.EXCEPTION_MAKER_NOT_FOUND)));
  }

  @Test
  void whenInvalidMediaType_thenReturns404() throws Exception {

    mockMvc
        .perform(get(API_BASE + "/makers/").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(jsonPath("$.success", is(false)))
        .andExpect(jsonPath("$.status", is(HttpStatus.NOT_ACCEPTABLE.value())))
        .andExpect(status().isNotAcceptable());
  }

  @Test
  public void whenCreateModel_withValidBody_shouldReturn201() throws Exception {

    // ModelServiceModel model = ModelServiceModel.builder().id(1L).name("A$").build();
    ModelServiceModel modelServiceModel = ModelServiceModel.builder().name("A4").build();
    MakerServiceModel updatedMaker = MakerServiceModel.builder().id(1L).name(MAKER_NAME).build();
    MakerResponseModel response = MakerResponseModel.builder().id(1L).name(MAKER_NAME).build();

    ModelCreateRequestModel createRequestModel = ModelCreateRequestModel.of("A4");
    given(modelMapper.map(createRequestModel, ModelServiceModel.class))
        .willReturn(modelServiceModel);

    // given(makerService.addModelToMaker(1L, modelServiceModel)).willReturn(updatedMaker);

    // given(modelMapper.map(any(MakerServiceModel.class), any())).willReturn(response);

    String expectedMessage = String.format(Constants.MODEL_CREATE_SUCCESS, "Banana", MAKER_NAME);

    performPost(API_BASE + "/makers/1/models", createRequestModel)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.message", is(expectedMessage)))
        .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())));
  }
}
