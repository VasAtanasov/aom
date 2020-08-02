package bg.autohouse.web.controllers;


import bg.autohouse.MvcPerformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// @Sql("/data.sql")
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchControllerTest extends MvcPerformer {
  static final String API_BASE = "/api/vehicles";
  static final String SEARCH_URL = API_BASE + "/offers/search";

  @Autowired protected MockMvc mockMvc;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  // @Test
  // void whenSearchOffers_fuelType_shouldReturn200() throws Exception {
  //   FilterRequest filterRequest =
  //       FilterRequest.builder().fuelType(FuelType.ELECTRIC.name()).build();

  //   performPost(SEARCH_URL, filterRequest)
  //       .andExpect(status().isOk())
  //       .andExpect(jsonPath("$.success", is(true)))
  //       .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
  //       .andExpect(jsonPath("$.data.page.empty", is(Boolean.FALSE)));
  // }

  // @Test
  // void whenSearchOffers_withPageNumber_shouldReturn200() throws Exception {
  //   FilterRequest filterRequest =
  //       FilterRequest.builder().fuelType(FuelType.ELECTRIC.name()).build();

  //   performPost(SEARCH_URL + "?page=2&size=5", filterRequest)
  //       .andExpect(status().isOk())
  //       .andExpect(jsonPath("$.success", is(true)))
  //       .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
  //       .andExpect(jsonPath("$.data.page.number", is(2)))
  //       .andExpect(jsonPath("$.data.page.size", is(5)))
  //       .andExpect(jsonPath("$.data.page.empty", is(Boolean.FALSE)));
  // }
}
