package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.account.AccountLog;
import bg.autohouse.data.models.enums.AccountLogType;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.geo.Address;
import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.repositories.AccountLogRepository;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.AccountDisabledOrClosed;
import bg.autohouse.errors.AccountNotFoundException;
import bg.autohouse.errors.LocationNotFoundException;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.service.models.account.*;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.service.services.AsyncUserLogger;
import bg.autohouse.service.validations.FieldValidator;
import bg.autohouse.service.validations.annotations.Required;
import bg.autohouse.util.Assert;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.StringGenericUtils;
import bg.autohouse.web.enums.RestMessage;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final LocationRepository locationRepository;
  private final AccountLogRepository accountLogRepository;
  private final ModelMapperWrapper modelMapper;
  private final AsyncUserLogger userLogger;
  private final FieldValidator fieldValidator;

  @Override
  @Transactional(readOnly = true)
  public boolean hasAccount(UUID id) {
    return accountRepository.existsByUserId(id);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean hasAccount(String username) {
    return accountRepository.existsByUserUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public AccountServiceModel loadAccountForUser(UUID userId) {
    Assert.notNull(userId, "User id is required.");
    User user = userRepository.findByIdWithRoles(userId).orElseThrow(NoSuchUserException::new);
    Account account =
        accountRepository.findByUserId(user.getId()).orElseThrow(AccountNotFoundException::new);
    if (!account.isEnabled()) {
      throw new AccountDisabledOrClosed(RestMessage.USER_ACCOUNT_DISABLED.name());
    }
    if (account.isClosed()) {
      throw new AccountDisabledOrClosed(RestMessage.USER_ACCOUNT_LOCKED.name());
    }
    return modelMapper.map(account, AccountServiceModel.class);
  }

  @Override
  @Transactional
  public AccountServiceModel createOrUpdatePrivateSellerAccount(
      AccountServiceModel model, UUID ownerId) {
    Assert.notNull(model, "No account model found");
    Assert.notNull(ownerId, "Account owner must be provided");
    User owner = userRepository.findByIdWithRoles(ownerId).orElseThrow(NoSuchUserException::new);
    validateModel(model, PrivateAccountRequiredFields.class);
    String displayNameToUse =
        Assert.has(model.getDisplayName()) ? model.getDisplayName() : generateRandomDisplayName();
    model.setDisplayName(displayNameToUse);
    Account privateAccount = accountRepository.findByUserId(owner.getId()).orElse(null);
    if (privateAccount == null) {
      privateAccount = Account.createPrivateAccount(model, owner);
    } else {
      privateAccount.setFirstName(model.getFirstName());
      privateAccount.setLastName(model.getLastName());
      privateAccount.setDisplayName(model.getDisplayName());
      privateAccount.setDescription(model.getDescription());
      privateAccount.getContact().setPhoneNumber(model.getContactDetails().getPhoneNumber());
      privateAccount.getContact().setWebLink(model.getContactDetails().getWebLink());
    }
    accountRepository.save(privateAccount);
    logAccountCreate(AccountType.PRIVATE, owner);
    return modelMapper.map(privateAccount, AccountServiceModel.class);
  }

  private String generateRandomDisplayName() {
    return "Auto" + StringGenericUtils.nextStringUpperDecimal(10);
  }

  @Getter
  @Required
  private static class PrivateAccountRequiredFields {
    private String firstName;
    private String lastName;
    private String accountType;
  }

  @Override
  @Transactional
  public AccountServiceModel createOrUpdateDealerAccount(AccountServiceModel model, UUID ownerId) {
    Assert.notNull(model, "No account model found");
    Assert.notNull(ownerId, "Account owner must be provided");
    User owner = userRepository.findByIdWithRoles(ownerId).orElseThrow(NoSuchUserException::new);
    validateModel(model, DealerAccountRequiredFields.class);
    Location location =
        locationRepository
            .findByPostalCode(model.getAddress().getLocationPostalCode())
            .orElseThrow(LocationNotFoundException::new);
    Account dealerAccount = accountRepository.findByUserId(owner.getId()).orElse(null);
    if (dealerAccount == null) {
      dealerAccount = Account.createDealerAccount(model, owner);
      String street = model.getAddress().getStreet();
      Address.createAddress(location, street, dealerAccount);
    } else {
      dealerAccount.setFirstName(model.getFirstName());
      dealerAccount.setLastName(model.getLastName());
      dealerAccount.setDisplayName(model.getDisplayName());
      dealerAccount.setDescription(model.getDescription());
      dealerAccount.getContact().setPhoneNumber(model.getContactDetails().getPhoneNumber());
      dealerAccount.getContact().setWebLink(model.getContactDetails().getWebLink());
      Address address = dealerAccount.getAddress();
      if (location.getId() != address.getLocation().getId()) {
        address.setLocation(location);
      }
      address.setStreet(model.getAddress().getStreet());
    }
    accountRepository.save(dealerAccount);
    logAccountCreate(AccountType.DEALER, owner);
    return modelMapper.map(dealerAccount, AccountServiceModel.class);
  }

  @Getter
  @Required
  private static class DealerAccountRequiredFields {
    private String firstName;
    private String lastName;
    private String displayName;
    private String description;
    private String contactDetailsPhoneNumber;
    private Integer addressLocationPostalCode;
    private String addressStreet;
    private String accountType;
  }

  private void validateModel(AccountServiceModel model, Class<?> target) {
    fieldValidator.validate(modelMapper.map(model, target));
  }

  private void logAccountCreate(AccountType accountType, User owner) {
    AccountLog accountLogCreate =
        AccountLog.builder()
            .accountLogType(AccountLogType.ACCOUNT_CREATED)
            .description(accountType.name())
            .user(owner)
            .build();
    accountLogRepository.save(accountLogCreate);

    switch (accountType) {
      case DEALER:
        userLogger.logUserAddDealerAccount(owner.getId());
        break;
      case PRIVATE:
        userLogger.logUserAddPrivateAccount(owner.getId());
        break;
    }
  }
}
