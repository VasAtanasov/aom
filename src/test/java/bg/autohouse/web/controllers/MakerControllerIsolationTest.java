package bg.autohouse.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.data.repositories.MakerRepository;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.services.InitialStateService;
import bg.autohouse.service.services.MakerService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.response.MakerResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MakerController.class)
public class MakerControllerIsolationTest extends MvcPerformer {
  static final String MAKER_NAME = "Audi";
  static final String API_BASE = "/api/vehicles";

  @Autowired protected MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private MakerRepository makerRepository;
  @MockBean private MakerService makerService;
  @MockBean private InitialStateService initialStateService;
  @MockBean private ModelMapperWrapper modelMapper;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @Test
  void whenValidInput_thenReturns200() throws Exception {

    MakerServiceModel model = MakerServiceModel.builder().id(1L).name(MAKER_NAME).build();
    MakerResponseModel response = MakerResponseModel.builder().id(1L).name(MAKER_NAME).build();

    given(makerService.getOne(1L)).willReturn(model);
    given(modelMapper.map(model, MakerResponseModel.class)).willReturn(response);

    performGet(API_BASE + "/makers/" + model.getId())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.data.maker.name", is(MAKER_NAME)))
        .andExpect(jsonPath("$.data.maker.id", is(1)));
  }

  // public ResultActions performGet(final String url) throws Exception {
  //   return mockMvc.perform(get(url).accept(APP_V1_MEDIA_TYPE_JSON)).andDo(print());
  // }
}
