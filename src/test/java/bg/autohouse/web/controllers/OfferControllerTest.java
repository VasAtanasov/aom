package bg.autohouse.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.FilterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = OfferController.class)
public class OfferControllerTest extends MvcPerformer {
  static final String API_BASE = "/api/offers/";
  static final String API_OFFERS_SEARCH =
      WebConfiguration.URL_API_BASE + WebConfiguration.OFFERS + "/search";

  @Autowired private MockMvc mockMvc;

  @MockBean private OfferService offerService;
  @MockBean private ModelMapperWrapper modelMapper;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @Test
  void whenGetMakers_thenReturns200() throws Exception {
    FilterRequest filterRequest = FilterRequest.builder().bodyStyle(BodyStyle.SEDAN.name()).build();
    String url = API_OFFERS_SEARCH + String.format("?page=%d&size=%d", 2, 50);
    performPost(url, filterRequest)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())));
  }
}
