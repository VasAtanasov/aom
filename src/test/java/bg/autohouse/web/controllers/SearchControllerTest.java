package bg.autohouse.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.web.models.request.FilterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
@Sql("/data.sql")
@TestPropertySource("classpath:test.properties")
public class SearchControllerTest extends MvcPerformer {
  static final String API_BASE = "/api/vehicles";
  static final String SEARCH_URL = API_BASE + "/offers/search";

  @Autowired protected MockMvc mockMvc;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @Test
  void whenSearchOffers_fuelType_shouldReturn200() throws Exception {
    FilterRequest filterRequest =
        FilterRequest.builder().fuelType(FuelType.ELECTRIC.name()).build();

    performPost(SEARCH_URL, filterRequest)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
        .andExpect(jsonPath("$.data.page.empty", is(Boolean.FALSE)));
  }

  @Test
  void whenSearchOffers_withPageNumber_shouldReturn200() throws Exception {
    FilterRequest filterRequest =
        FilterRequest.builder().fuelType(FuelType.ELECTRIC.name()).build();

    performPost(SEARCH_URL + "?page=2&size=5", filterRequest)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
        .andExpect(jsonPath("$.data.page.number", is(2)))
        .andExpect(jsonPath("$.data.page.size", is(5)))
        .andExpect(jsonPath("$.data.page.empty", is(Boolean.FALSE)));
  }
}
