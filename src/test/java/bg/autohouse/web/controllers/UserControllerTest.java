package bg.autohouse.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
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
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.UserChangePasswordRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
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
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/location.sql")
@Sql("/makersModelsTrims.sql")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/users";
  private static final UserLoginRequest LOGIN_REQUEST_ROOT =
      UserLoginRequest.of(DatabaseSeeder.ROOT_USERNAME, "123");
  private static final UserLoginRequest LOGIN_REQUEST_USER =
      UserLoginRequest.of(DatabaseSeeder.USERNAME, "123");

  @Autowired protected MockMvc mockMvc;

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
  void initHeaders() throws Exception {
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
    MockMultipartFile image =
        new MockMultipartFile(
            "images", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image content".getBytes());
    for (OfferCreateRequestWrapper createRequestWrapper : offersRequests) {
      OfferCreateRequest request = createRequestWrapper.getOffer();
      request.getImages().add(image);
      OfferServiceModel offer = offerService.createOffer(request, user.getId());
      offerIds.add(offer.getId());
    }
  }

  @Test
  void when_changePassword_thenReturn200() throws Exception {
    UserChangePasswordRequest request =
        UserChangePasswordRequest.builder()
            .oldPassword("123")
            .newPassword("1234")
            .confirmPassword("1234")
            .build();
    performPost(API_BASE + "/password/update", request, headers).andExpect(status().isOk());
  }

  @Test
  void when_changePassword_invalidOldPassword_thenReturn400() throws Exception {
    UserChangePasswordRequest request =
        UserChangePasswordRequest.builder()
            .oldPassword("1234")
            .newPassword("1234")
            .confirmPassword("1234")
            .build();
    performPost(API_BASE + "/password/update", request, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.INVALID_PASSWORD.name())));
  }

  @Test
  void when_changePassword_missingOldPassword_thenReturn400() throws Exception {
    UserChangePasswordRequest request =
        UserChangePasswordRequest.builder()
            .oldPassword(null)
            .newPassword("1234")
            .confirmPassword("1234")
            .build();
    performPost(API_BASE + "/password/update", request, headers)
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is(RestMessage.PARAMETER_VALIDATION_FAILURE.name())));
  }

  @Test
  void when_addToFavorites_validIdOfferId_thenReturn200() throws Exception {
    assertThat(offerIds).isNotEmpty();
    String offerId = offerIds.get(0);
    performGet(
            API_BASE + "/offer/add-to-favorites/" + offerId, getAuthHeadersFor(LOGIN_REQUEST_USER))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(not(empty()))));
  }

  @Test
  void when_getUserOffers_thenReturn200() throws Exception {
    performGet(API_BASE + "/offer/list", headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.empty", is(Boolean.FALSE)));
  }

  @Test
  void when_toggleOfferActive_thenReturn200() throws Exception {
    assertThat(offerIds).isNotEmpty();
    String offerId = offerIds.get(0);
    performGet(API_BASE + "/offer/toggle-active/" + offerId, headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(Boolean.FALSE)));
  }
}
