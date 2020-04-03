package bg.autohouse.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.data.models.enums.Seller;
import bg.autohouse.web.models.request.DealershipRegisterRequest;
import bg.autohouse.web.models.request.RegisterRequest;
import bg.autohouse.web.models.request.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/users";

  private static final UserRegisterRequest VALID_USER_REGISTER_MODEL =
      UserRegisterRequest.builder()
          .username("username@mail.com")
          .password("password")
          .confirmPassword("password")
          .phoneNumber("phoneNumber1")
          .firstName("firstName")
          .lastName("lastName")
          .seller(Seller.PRIVATE.name())
          .build();

  private static final UserRegisterRequest VALID_DEALER_REGISTER_MODEL =
      UserRegisterRequest.builder()
          .username("dealer@mail.com")
          .password("password")
          .confirmPassword("password")
          .phoneNumber("phoneNumber2")
          .firstName("firstName")
          .lastName("lastName")
          .seller(Seller.DEALER.name())
          .build();

  private static final DealershipRegisterRequest VALID_DEALERSHIP_REGISTER_MODEL =
      DealershipRegisterRequest.builder()
          .name("name")
          .description("description")
          .address("address")
          .locationId(2L)
          .build();

  @Autowired protected MockMvc mockMvc;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @Test
  void whenRegisterPrivateSeller_thenReturns201() throws Exception {
    RegisterRequest registerRequest =
        RegisterRequest.builder().user(VALID_USER_REGISTER_MODEL).build();

    performPost(API_BASE + "/register", registerRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())));
  }

  @Test
  @Sql("/location.sql")
  void whenRegisterDealer_thenReturns201() throws Exception {

    RegisterRequest registerRequest =
        RegisterRequest.builder()
            .user(VALID_DEALER_REGISTER_MODEL)
            .dealership(VALID_DEALERSHIP_REGISTER_MODEL)
            .build();

    performPost(API_BASE + "/register", registerRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())));
  }
}
