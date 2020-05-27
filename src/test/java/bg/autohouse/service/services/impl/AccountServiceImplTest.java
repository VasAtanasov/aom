package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.errors.RequiredFieldMissing;
import bg.autohouse.service.models.account.AccountServiceModel;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.account.DealerAccountCreateUpdateRequest;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Sql("/location.sql")
@TestPropertySource("classpath:test.properties")
public class AccountServiceImplTest {

  @Autowired private AccountService accountService;
  @Autowired private UserRepository userRepository;
  @Autowired private ModelMapperWrapper modelMapper;

  @Test
  void when_createDealerAccount_missingRequiredFields_shouldThrow() {
    User user = userRepository.findByUsernameIgnoreCase(DatabaseSeeder.ROOT_USERNAME).orElse(null);
    assertThat(user).isNotNull();
    AccountServiceModel model = AccountServiceModel.builder().firstName("firstName").build();
    Throwable thrown =
        catchThrowable(() -> accountService.createDealerAccount(model, user.getId()));
    assertThat(thrown)
        .isInstanceOf(RequiredFieldMissing.class)
        .hasMessageStartingWith("Required field missing");
  }

  @Test
  void when_createDealerAccount_missingUser_shouldThrow() {
    User user = userRepository.findByUsernameIgnoreCase(DatabaseSeeder.ROOT_USERNAME).orElse(null);
    assertThat(user).isNotNull();
    AccountServiceModel model = AccountServiceModel.builder().firstName("firstName").build();
    Throwable thrown =
        catchThrowable(() -> accountService.createDealerAccount(model, UUID.randomUUID()));
    assertThat(thrown)
        .isInstanceOf(NoSuchUserException.class)
        .hasMessageStartingWith(RestMessage.USER_NOT_FOUND.name());
  }

  @Test
  void when_createDealerAccount_validModel_shouldCreate() {
    User user = userRepository.findByUsernameIgnoreCase(DatabaseSeeder.ROOT_USERNAME).orElse(null);
    assertThat(user).isNotNull();
    DealerAccountCreateUpdateRequest request =
        DealerAccountCreateUpdateRequest.builder()
            .firstName("firstName")
            .lastName("lastName")
            .displayName("displayedName")
            .description("description")
            .contactDetailsPhoneNumber("phoneNumber")
            .addressLocationPostalCode(9000)
            .addressStreet("street")
            .accountType(AccountType.DEALER.name())
            .build();
    AccountServiceModel model = modelMapper.map(request, AccountServiceModel.class);
    AccountServiceModel account = accountService.createDealerAccount(model, user.getId());

    assertThat(account.getId()).isNotNull();
  }
}
