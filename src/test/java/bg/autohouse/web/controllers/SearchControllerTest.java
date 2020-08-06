package bg.autohouse.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.service.models.account.AccountServiceModel;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ImageResizer;
import bg.autohouse.utils.OfferCreateRequestWrapper;
import bg.autohouse.web.models.request.FilterRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import bg.autohouse.web.models.response.FilterResponseModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/location.sql")
@Sql("/makersModelsTrims.sql")
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SearchControllerTest extends MvcPerformer {
  static final String API_BASE = "/api/vehicles";
  static final String SEARCH_URL = API_BASE + "/offers/search";
  static final UserLoginRequest LOGIN_REQUEST_ROOT =
      UserLoginRequest.of(DatabaseSeeder.ROOT_USERNAME, "123");

  @Autowired MockMvc mockMvc;

  @Autowired UserService userService;
  @Autowired AccountService accountService;
  @Autowired OfferService offerService;
  @Autowired ObjectMapper mapper;

  @MockBean ImageResizer imageResizer;
  @MockBean MediaFileService mediaFileService;

  HttpHeaders headers;
  User user;
  List<OfferCreateRequestWrapper> offersRequests = new ArrayList<>();
  List<String> offerIds = new ArrayList<>();

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @BeforeEach
  void initHeaders() throws Exception, JsonMappingException, JsonProcessingException {
    if (headers == null) headers = getAuthHeadersFor(LOGIN_REQUEST_ROOT);
    if (user == null) {
      user = userService.fetchUserByUsername(DatabaseSeeder.ROOT_USERNAME);
      if (user != null && !user.isHasAccount()) {
        String content = JsonReaderUtil.readJsonContent("dealer-account.json");
        AccountServiceModel createRequest = mapper.readValue(content, AccountServiceModel.class);
        accountService.createOrUpdateDealerAccount(createRequest, user.getId());
      }
    }
    if (offersRequests.isEmpty()) {
      String content = JsonReaderUtil.readJsonContent("offers-create-models.json");
      OfferCreateRequestWrapper[] offersRequestString =
          mapper.readValue(content, OfferCreateRequestWrapper[].class);
      offersRequests.addAll(Arrays.asList(offersRequestString));
    }
    assertThat(user).isNotNull();
    assertThat(offersRequests).isNotEmpty();
    when(imageResizer.toJpgDownscaleToSize(any(InputStream.class)))
        .thenReturn("test image".getBytes());
    MediaFile mediaFile = new MediaFile();
    mediaFile.setOriginalFilename("image");
    when(mediaFileService.storeFile(
            any(byte[].class),
            any(String.class),
            any(MediaFunction.class),
            any(String.class),
            any(String.class),
            any(UUID.class)))
        .thenReturn(mediaFile);
    MockMultipartFile image = getMockedMediaFile();
    for (OfferCreateRequestWrapper createRequestWrapper : offersRequests) {
      OfferCreateRequest request = createRequestWrapper.getOffer();
      request.getImages().add(image);
      OfferServiceModel offer = offerService.createOffer(request, user.getId());
      offerIds.add(offer.getId());
    }
  }

  @Test
  void whenSearchOffers_fuelType_shouldReturn200() throws Exception {
    FilterRequest filterRequest = FilterRequest.builder().fuelType(FuelType.DIESEL.name()).build();
    performPost(SEARCH_URL, filterRequest)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
        .andExpect(jsonPath("$.data.page.empty", is(Boolean.FALSE)));
  }

  @Test
  void whenSearchOffers_withPageNumber_shouldReturn200() throws Exception {
    FilterRequest filterRequest = FilterRequest.builder().fuelType(FuelType.DIESEL.name()).build();
    performPost(SEARCH_URL + "?size=5", filterRequest)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
        .andExpect(jsonPath("$.data.page.size", is(5)))
        .andExpect(jsonPath("$.data.page.empty", is(Boolean.FALSE)));
  }

  @Test
  void when_save_list_deleteFilter_shouldReturn200() throws Exception {
    FilterRequest filterRequest =
        FilterRequest.builder()
            .makerName("Volkswagen")
            .modelName("Passat")
            .fuelType(FuelType.DIESEL.name())
            .build();
    performPost(SEARCH_URL + "/save", filterRequest, headers).andExpect(status().isOk());
    MvcResult result =
        performGet(SEARCH_URL + "/list", headers)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andReturn();
    String contentAsString = result.getResponse().getContentAsString();
    List<FilterResponseModel> filters =
        mapper.readValue(contentAsString, new TypeReference<List<FilterResponseModel>>() {});
    assertThat(filters).isNotEmpty();
    FilterResponseModel filter = filters.get(0);
    performDelete(SEARCH_URL + "/saved-search/" + filter.getId(), headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(filter.getId())));
  }

  @Test
  void when_findOffersByIds_shouldReturn200() throws Exception {
    assertThat(offerIds).isNotEmpty();
    String offerId = offerIds.get(0);
    List<UUID> favorites = userService.addToFavorites(user.getId(), UUID.fromString(offerId));
    performPost(SEARCH_URL + "/favorites", favorites, headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.empty", is(Boolean.FALSE)));
    favorites = userService.addToFavorites(user.getId(), UUID.fromString(offerId));
    performPost(SEARCH_URL + "/favorites", favorites, headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.empty", is(Boolean.TRUE)));
  }

  MockMultipartFile getMockedMediaFile() {
    return new MockMultipartFile(
        "images", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image content".getBytes());
  }
}
