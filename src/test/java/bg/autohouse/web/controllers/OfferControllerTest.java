package bg.autohouse.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.service.models.account.AccountServiceModel;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ImageResizer;
import bg.autohouse.utils.OfferCreateRequestWrapper;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import bg.autohouse.web.models.request.offer.VehicleCreateRequest;
import bg.autohouse.web.models.response.offer.OfferDetailsResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/location.sql")
@Sql("/makersModelsTrims.sql")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTest extends MvcPerformer {
  static final String API_BASE = "/api/vehicles/offers";
  static final UserLoginRequest LOGIN_REQUEST_ROOT =
      UserLoginRequest.of(DatabaseSeeder.ROOT_USERNAME, "123");

  @Autowired MockMvc mockMvc;
  @Autowired UserService userService;
  @Autowired AccountService accountService;
  @Autowired ObjectMapper mapper;

  @MockBean ImageResizer imageResizer;
  @MockBean MediaFileService mediaFileService;

  HttpHeaders headers;
  User user;
  List<OfferCreateRequestWrapper> offersRequests = new ArrayList<>();

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
    assertThat(user).isNotNull();
    assertThat(offersRequests).isNotEmpty();
  }

  @Test
  void when_createOffer_validOfferData_shouldReturn201() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    OfferCreateRequestWrapper createRequestWrapper = offersRequests.get(0);
    MvcResult result = createOffer(createRequestWrapper);
    assertThat(result).isNotNull();
    OfferServiceModel offerServiceModel =
        mapper.readValue(getStringContentDataNode(result), OfferServiceModel.class);
    assertThat(offerServiceModel).isNotNull();
    assertThat(offerServiceModel.getId()).isNotNull();
    performDelete(API_BASE + "/" + offerServiceModel.getId(), headers)
        .andExpect(ok)
        .andExpect(jsonPath("$", is(offerServiceModel.getId().toString())));
  }

  @Test
  void when_updateOffer_validOfferData_shouldReturn200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    OfferCreateRequestWrapper createRequestWrapper = offersRequests.get(0);
    MvcResult result = createOffer(createRequestWrapper);
    assertThat(result).isNotNull();
    OfferServiceModel offerServiceModel =
        mapper.readValue(getStringContentDataNode(result), OfferServiceModel.class);
    assertThat(offerServiceModel).isNotNull();
    assertThat(offerServiceModel.getId()).isNotNull();
    createRequestWrapper.getOffer().setPrice(22000);
    MockMultipartHttpServletRequestBuilder performBuilderUpdate =
        offerToFormData(
            createRequestWrapper, API_BASE + "/update/" + offerServiceModel.getId().toString());
    getMockMvc()
        .perform(
            performBuilderUpdate.headers(headers).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andExpect(ok);
    performDelete(API_BASE + "/" + offerServiceModel.getId(), headers)
        .andExpect(ok)
        .andExpect(jsonPath("$", is(offerServiceModel.getId().toString())));
  }

  @Test
  void when_loadOfferForEdit_validOfferId_shouldReturn200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    OfferCreateRequestWrapper createRequestWrapper = offersRequests.get(0);
    MvcResult result = createOffer(createRequestWrapper);
    assertThat(result).isNotNull();
    OfferServiceModel offerServiceModel =
        mapper.readValue(getStringContentDataNode(result), OfferServiceModel.class);
    assertThat(offerServiceModel).isNotNull();
    assertThat(offerServiceModel.getId()).isNotNull();
    performGet(API_BASE + "/load-for-edit/" + offerServiceModel.getId(), headers)
        .andExpect(ok)
        .andExpect(jsonPath("$.id", is(offerServiceModel.getId().toString())));
    performDelete(API_BASE + "/" + offerServiceModel.getId(), headers)
        .andExpect(ok)
        .andExpect(jsonPath("$", is(offerServiceModel.getId().toString())));
  }

  @Test
  void when_viewOffer_validOfferId_shouldReturn200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    OfferCreateRequestWrapper createRequestWrapper = offersRequests.get(0);
    MvcResult result = createOffer(createRequestWrapper);
    assertThat(result).isNotNull();
    OfferServiceModel offerServiceModel =
        mapper.readValue(getStringContentDataNode(result), OfferServiceModel.class);
    assertThat(offerServiceModel).isNotNull();
    assertThat(offerServiceModel.getId()).isNotNull();
    MvcResult resultDetails =
        performGet(API_BASE + "/details/" + offerServiceModel.getId(), headers)
            .andExpect(ok)
            .andExpect(jsonPath("$.data.offer.id", is(offerServiceModel.getId().toString())))
            .andExpect(
                jsonPath("$.data.offer.hitCount", greaterThan(offerServiceModel.getHitCount())))
            .andReturn();
    String jsonDetails = getStringContentDataNode(resultDetails);
    OfferDetailsResponseWrapper wrapper =
        convertJSONStringToObject(jsonDetails, OfferDetailsResponseWrapper.class);
    performGet(API_BASE + "/details/" + offerServiceModel.getId() + "?pr=true", headers)
        .andExpect(ok)
        .andExpect(jsonPath("$.data.offer.id", is(offerServiceModel.getId().toString())))
        .andExpect(jsonPath("$.data.offer.hitCount", equalTo(wrapper.getOffer().getHitCount())));
    performDelete(API_BASE + "/" + offerServiceModel.getId(), headers)
        .andExpect(ok)
        .andExpect(jsonPath("$", is(offerServiceModel.getId().toString())));
  }

  @Test
  void when_getLatestOffers_shouldReturn200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    List<String> offerIds = new ArrayList<>();
    for (OfferCreateRequestWrapper createRequestWrapper : offersRequests) {
      MvcResult result = createOffer(createRequestWrapper);
      assertThat(result).isNotNull();
      OfferServiceModel offerServiceModel =
          mapper.readValue(getStringContentDataNode(result), OfferServiceModel.class);
      assertThat(offerServiceModel).isNotNull();
      assertThat(offerServiceModel.getId()).isNotNull();
      offerIds.add(offerServiceModel.getId());
    }
    performGet(API_BASE + "/top", headers)
        .andExpect(ok)
        .andExpect(jsonPath("$.data", hasSize(offerIds.size())));
    for (String id : offerIds) {
      performDelete(API_BASE + "/" + id, headers).andExpect(ok).andExpect(jsonPath("$", is(id)));
    }
  }

  @Test
  void when_getOfferStatistics_shouldReturn200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    OfferCreateRequestWrapper createRequestWrapper = offersRequests.get(0);
    MvcResult result = createOffer(createRequestWrapper);
    assertThat(result).isNotNull();
    OfferServiceModel offerServiceModel =
        mapper.readValue(getStringContentDataNode(result), OfferServiceModel.class);
    assertThat(offerServiceModel).isNotNull();
    assertThat(offerServiceModel.getId()).isNotNull();
    performGet(API_BASE + "/statistics")
        .andExpect(ok)
        .andExpect(jsonPath("$.maxPrice", is(offerServiceModel.getPrice())));
    performDelete(API_BASE + "/" + offerServiceModel.getId(), headers)
        .andExpect(ok)
        .andExpect(jsonPath("$", is(offerServiceModel.getId().toString())));
  }

  @Test
  void when_getUserOffersCount_shouldReturn200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    OfferCreateRequestWrapper createRequestWrapper = offersRequests.get(0);
    MvcResult result = createOffer(createRequestWrapper);
    assertThat(result).isNotNull();
    OfferServiceModel offerServiceModel =
        mapper.readValue(getStringContentDataNode(result), OfferServiceModel.class);
    assertThat(offerServiceModel).isNotNull();
    assertThat(offerServiceModel.getId()).isNotNull();
    AccountServiceModel account = accountService.loadAccountForUser(user.getId());
    performGet(API_BASE + "/count/" + account.getId() + "/offers")
        .andExpect(ok)
        .andExpect(jsonPath("$", is(1)));
    performDelete(API_BASE + "/" + offerServiceModel.getId(), headers)
        .andExpect(ok)
        .andExpect(jsonPath("$", is(offerServiceModel.getId().toString())));
  }

  String getStringContentDataNode(MvcResult result) throws Exception {
    String contentAsString = result.getResponse().getContentAsString();
    JsonNode node = mapper.readTree(contentAsString);
    assertThat(node.has("data")).isTrue();
    return node.path("data").toString();
  }

  MvcResult createOffer(OfferCreateRequestWrapper createRequestWrapper) throws Exception {
    if (createRequestWrapper == null) {
      return null;
    }
    ResultMatcher created = MockMvcResultMatchers.status().isCreated();
    MockMultipartHttpServletRequestBuilder performBuilder =
        offerToFormData(createRequestWrapper, API_BASE);
    ResultActions resultActions =
        getMockMvc()
            .perform(
                performBuilder.headers(headers).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
            .andExpect(created);
    MvcResult result = resultActions.andReturn();
    return result;
  }

  MockMultipartHttpServletRequestBuilder offerToFormData(
      OfferCreateRequestWrapper createRequestWrapper, String url) {
    MockMultipartHttpServletRequestBuilder builder = multipart(url);
    OfferCreateRequest request = createRequestWrapper.getOffer();
    createRequestWrapper
        .getImages()
        .forEach(
            i ->
                builder.file(
                    new MockMultipartFile("images", i, MediaType.IMAGE_JPEG_VALUE, i.getBytes())));
    builder
        .param("price", String.valueOf(request.getPrice()))
        .param("addressLocationPostalCode", String.valueOf(request.getAddressLocationPostalCode()))
        .param("description", request.getDescription())
        .param("contactDetailsPhoneNumber", request.getContactDetailsPhoneNumber())
        .param("contactDetailsWebLink", request.getContactDetailsWebLink())
        .param("contactDetailsWebLink", request.getContactDetailsWebLink())
        .param("mainPhoto", request.getMainPhoto());
    VehicleCreateRequest vehicle = request.getVehicle();
    builder
        .param("vehicle.bodyStyle", vehicle.getBodyStyle())
        .param("vehicle.fuelType", vehicle.getFuelType())
        .param("vehicle.transmission", vehicle.getTransmission())
        .param("vehicle.state", vehicle.getState())
        .param("vehicle.drive", vehicle.getDrive())
        .param("vehicle.color", vehicle.getColor())
        .param("vehicle.hasAccident", String.valueOf(vehicle.isHasAccident()))
        .param("vehicle.doors", String.valueOf(vehicle.getDoors()))
        .param("vehicle.mileage", String.valueOf(vehicle.getMileage()))
        .param("vehicle.year", String.valueOf(vehicle.getYear()))
        .param("vehicle.makerName", vehicle.getMakerName())
        .param("vehicle.modelName", vehicle.getModelName())
        .param("vehicle.trim", vehicle.getTrim());
    vehicle.getFeatures().forEach(f -> builder.param("vehicle.features", f));
    return builder;
  }
}
