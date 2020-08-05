package bg.autohouse.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.user.UserRowServiceModel;
import bg.autohouse.service.services.UserService;
import bg.autohouse.utils.RestResponsePage;
import bg.autohouse.web.models.request.ChangeRoleRequest;
import bg.autohouse.web.models.request.UserLoginRequest;
import bg.autohouse.web.models.request.account.AccountCreateRequest;
import bg.autohouse.web.models.request.account.AccountWrapper;
import bg.autohouse.web.models.wrappers.ListWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@Sql("/location.sql")
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest extends MvcPerformer {
  private static final String API_BASE = "/api/admin";
  private static final UserLoginRequest LOGIN_REQUEST_ROOT =
      UserLoginRequest.of(DatabaseSeeder.ROOT_USERNAME, "123");

  private static final List<String> usernames =
      List.of(
          "ernestina_altenwerth@gmail.com",
          "jordy_beatty20@yahoo.com",
          "noe36@yahoo.com",
          "romaine_terry40@gmail.com",
          "bethel91@gmail.com");

  @Autowired protected MockMvc mockMvc;
  @Autowired private UserService userService;
  @Autowired ObjectMapper mapper;
  private HttpHeaders headers;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @BeforeEach
  void initHeaders() throws Exception {
    if (headers == null) headers = getAuthHeadersFor(LOGIN_REQUEST_ROOT);
  }

  @Test
  void when_getUsersList_thenReturn200() throws Exception {
    ResultActions resultActions =
        performGet(API_BASE + "/users/list", headers).andExpect(status().isOk());
    MvcResult result = resultActions.andReturn();
    String contentAsString = result.getResponse().getContentAsString();
    RestResponsePage<UserRowServiceModel> page =
        mapper.readValue(
            contentAsString, new TypeReference<RestResponsePage<UserRowServiceModel>>() {});
    assertThat(page).isNotNull();
    assertThat(page.getContent()).isNotEmpty();
  }

  @Test
  void when_fetchUserDetails_validUserId_thenReturn200() throws Exception {
    User user = userService.fetchUserByUsername(DatabaseSeeder.USERNAME);
    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotNull();
    performGet(API_BASE + "/user/details/" + user.getId(), headers).andExpect(status().isOk());
  }

  @Test
  void when_fetchUserDetails_invalidUserId_thenReturn404() throws Exception {
    performGet(API_BASE + "/user/details/" + UUID.randomUUID(), headers)
        .andExpect(status().isNotFound());
  }

  @Test
  void when_toggleActive_validUserId_thenReturn200() throws Exception {
    User user = userService.fetchUserByUsername(DatabaseSeeder.USERNAME);
    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotNull();
    performGet(API_BASE + "/user/toggle-active/" + user.getId(), headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(false)));
    performGet(API_BASE + "/user/toggle-active/" + user.getId(), headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(true)));
  }

  @Test
  void when_toggleActive_invalidUserId_thenReturn404() throws Exception {
    performGet(API_BASE + "/user/toggle-active/" + UUID.randomUUID(), headers)
        .andExpect(status().isNotFound());
  }

  @Test
  void when_updateUserRoles_validRole_thenReturn200() throws Exception {
    User user = userService.fetchUserByUsername(DatabaseSeeder.USERNAME);
    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotNull();
    ChangeRoleRequest cRequest =
        ChangeRoleRequest.builder()
            .userId(user.getId())
            .currentRole(Role.USER.name())
            .newRole(Role.ADMIN.name())
            .build();
    performPost(API_BASE + "/user/update-roles/", cRequest, headers).andExpect(status().isOk());
  }

  @Test
  void when_updateUserRoles_invalidUserId_thenReturn404() throws Exception {
    UUID invalidUserId = UUID.randomUUID();
    ChangeRoleRequest cRequest =
        ChangeRoleRequest.builder()
            .userId(invalidUserId)
            .currentRole(Role.USER.name())
            .newRole(Role.ADMIN.name())
            .build();
    performPost(API_BASE + "/user/update-roles/", cRequest, headers)
        .andExpect(status().isNotFound());
  }

  @Test
  void when_updateUserRoles_invalidCurrentRole_thenReturn400() throws Exception {
    User user = userService.fetchUserByUsername(DatabaseSeeder.USERNAME);
    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotNull();
    ChangeRoleRequest cRequest =
        ChangeRoleRequest.builder()
            .userId(user.getId())
            .currentRole("Invalid role")
            .newRole(Role.ADMIN.name())
            .build();
    performPost(API_BASE + "/user/update-roles/", cRequest, headers)
        .andExpect(status().isBadRequest());
  }

  @Test
  void when_updateUserRoles_invalidNewRole_thenReturn400() throws Exception {
    User user = userService.fetchUserByUsername(DatabaseSeeder.USERNAME);
    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotNull();
    ChangeRoleRequest cRequest =
        ChangeRoleRequest.builder()
            .userId(user.getId())
            .currentRole(Role.ADMIN.name())
            .newRole("Invalid role")
            .build();
    performPost(API_BASE + "/user/update-roles/", cRequest, headers)
        .andExpect(status().isBadRequest());
  }

  @Test
  void when_bulkInsert_validUsernames_shouldReturn200() throws Exception {
    ResultActions resultActions =
        performPost(API_BASE + "/users/bulk", ListWrapper.of(usernames), headers)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.not(Matchers.empty())));
    MvcResult result = resultActions.andReturn();
    String contentAsString = result.getResponse().getContentAsString();
    List<UserServiceModel> registeredUsers =
        mapper.readValue(contentAsString, new TypeReference<List<UserServiceModel>>() {});
    String content = JsonReaderUtil.readJsonContent("accounts.json");
    AccountCreateRequest[] requests = mapper.readValue(content, AccountCreateRequest[].class);
    int[] index = {0};
    List<AccountWrapper> accounts =
        registeredUsers.stream()
            .map(
                u -> {
                  AccountCreateRequest request = requests[index[0]];
                  index[0] = ++index[0];
                  return AccountWrapper.of(u.getId(), u.getUsername(), request);
                })
            .collect(Collectors.toList());
    System.out.println(requests);
    performPost(API_BASE + "/accounts/bulk", accounts, headers)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.greaterThan(0)));
  }
}
