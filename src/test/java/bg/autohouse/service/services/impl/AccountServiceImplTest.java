package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.repositories.AccountLogRepository;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.AccountDisabledOrClosed;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.service.models.account.AccountServiceModel;
import bg.autohouse.service.services.AsyncUserLogger;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.account.DealerAccountCreateUpdateRequest;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

  @InjectMocks AccountServiceImpl accountService;

  @Mock AccountRepository accountRepository;
  @Mock UserRepository userRepository;
  @Mock LocationRepository locationRepository;
  @Mock AccountLogRepository accountLogRepository;
  @Mock ModelMapperWrapper modelMapper;
  @Mock AsyncUserLogger userLogger;

  @Test
  void when_hasAccount_existingId_shouldReturnTrue() {
    when(accountRepository.existsByUserId(any(UUID.class))).thenReturn(true);
    boolean hasAccount = accountService.hasAccount(UUID.randomUUID());
    assertThat(hasAccount).isTrue();
  }

  @Test
  void when_hasAccount_existingUsername_shouldReturnTrue() {
    when(accountRepository.existsByUserUsername(any(String.class))).thenReturn(true);
    boolean hasAccount = accountService.hasAccount("Username");
    assertThat(hasAccount).isTrue();
  }

  @Test
  void when_loadAccountForUser_disabled_shouldThrow() {
    User user = new User();
    user.setId(UUID.randomUUID());
    when(userRepository.findByIdWithRoles(any(UUID.class))).thenReturn(Optional.of(user));
    Account account = new Account();
    account.setEnabled(false);
    when(accountRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(account));
    Throwable thrown = catchThrowable(() -> accountService.loadAccountForUser(user.getId()));
    assertThat(thrown)
        .isInstanceOf(AccountDisabledOrClosed.class)
        .hasMessageStartingWith(RestMessage.USER_ACCOUNT_DISABLED.name());
  }

  @Test
  void when_loadAccountForUser_closed_shouldThrow() {
    User user = new User();
    user.setId(UUID.randomUUID());
    when(userRepository.findByIdWithRoles(any(UUID.class))).thenReturn(Optional.of(user));
    Account account = new Account();
    account.setEnabled(true);
    account.setClosed(true);
    when(accountRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(account));
    Throwable thrown = catchThrowable(() -> accountService.loadAccountForUser(user.getId()));
    assertThat(thrown)
        .isInstanceOf(AccountDisabledOrClosed.class)
        .hasMessageStartingWith(RestMessage.USER_ACCOUNT_LOCKED.name());
  }

  @Test
  void when_createDealerAccount_missingRequiredFields_shouldThrow() {
    User user = new User();
    user.setId(UUID.randomUUID());
    AccountServiceModel model = AccountServiceModel.builder().firstName("firstName").build();
    Throwable thrown =
        catchThrowable(() -> accountService.createOrUpdateDealerAccount(model, user.getId()));
    assertThat(thrown)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith(RestMessage.ACCOUNT_MISSING_LAST_NAME.name());
  }

  @Test
  void when_createDealerAccount_missingUser_shouldThrow() {
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
    AccountServiceModel model =
        SingletonModelMapper.mapper().map(request, AccountServiceModel.class);
    Throwable thrown =
        catchThrowable(() -> accountService.createOrUpdateDealerAccount(model, UUID.randomUUID()));
    assertThat(thrown)
        .isInstanceOf(NoSuchUserException.class)
        .hasMessageStartingWith(RestMessage.USER_NOT_FOUND.name());
  }

  @Test
  void when_createDealerAccount_validModel_shouldCreate() {
    User user = new User();
    user.setId(UUID.randomUUID());
    when(userRepository.findByIdWithRoles(any(UUID.class))).thenReturn(Optional.of(user));
    Location location = new Location();
    when(locationRepository.findFirstByPostalCode(anyInt())).thenReturn(Optional.of(location));
    when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
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
    AccountServiceModel model =
        SingletonModelMapper.mapper().map(request, AccountServiceModel.class);
    when(modelMapper.map(any(Account.class), eq(AccountServiceModel.class)))
        .thenAnswer(
            invocationOnMock ->
                SingletonModelMapper.mapper()
                    .map(invocationOnMock.getArguments()[0], AccountServiceModel.class));
    AccountServiceModel account = accountService.createOrUpdateDealerAccount(model, user.getId());
    assertThat(account.getFirstName()).isEqualTo(request.getFirstName());
    assertThat(account.getLastName()).isEqualTo(request.getLastName());
  }
}
