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
import bg.autohouse.errors.ResourceAlreadyExistsException;
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

  // private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  @Transactional(readOnly = true)
  public boolean hasAccount(UUID id) {
    return accountRepository.existsByOwnerId(id);
  }

  @Override
  @Transactional(readOnly = true)
  public AccountServiceModel loadAccountForUser(UUID userId) {
    Assert.notNull(userId, "User id is required.");
    User user = userRepository.findById(userId).orElseThrow(NoSuchUserException::new);
    Account account = // Account id and user id is sam due to @MapsId
        accountRepository.findById(user.getId()).orElseThrow(AccountNotFoundException::new);
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
  public AccountServiceModel createPrivateSellerAccount(AccountServiceModel model, UUID ownerId) {
    Assert.notNull(model, "No account model found");
    Assert.notNull(ownerId, "Account owner must be provided");
    User owner = userRepository.findById(ownerId).orElseThrow(NoSuchUserException::new);
    if (owner.isHasAccount()) {
      throw new ResourceAlreadyExistsException(RestMessage.USER_ALREADY_HAS_ACCOUNT);
    }
    validateModel(model, PrivateAccountRequiredFields.class);
    String displayNameToUse =
        Assert.has(model.getDisplayName()) ? model.getDisplayName() : generateRandomDisplayName();
    Account privateAccount = modelMapper.map(model, Account.class);
    privateAccount.setMaxOffersCount(AccountType.PRIVATE.resolveMaxOffersCount());
    privateAccount.setOwner(owner);
    privateAccount.setAccountType(AccountType.PRIVATE);
    privateAccount.setDisplayName(displayNameToUse);
    privateAccount.setEnabled(Boolean.TRUE);
    accountRepository.save(privateAccount);
    owner.setHasAccount(Boolean.TRUE);
    userRepository.save(owner);
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
  public AccountServiceModel createDealerAccount(AccountServiceModel model, UUID ownerId) {
    Assert.notNull(model, "No account model found");
    Assert.notNull(ownerId, "Account owner must be provided");
    User owner = userRepository.findById(ownerId).orElseThrow(NoSuchUserException::new);
    if (owner.isHasAccount()) {
      throw new ResourceAlreadyExistsException(RestMessage.USER_ALREADY_HAS_ACCOUNT);
    }
    validateModel(model, DealerAccountRequiredFields.class);
    Location location =
        locationRepository
            .findById(model.getAddress().getLocationId())
            .orElseThrow(LocationNotFoundException::new);
    Account dealerAccount = modelMapper.map(model, Account.class);
    dealerAccount.setId(null); // modelMapper sets id
    dealerAccount.setMaxOffersCount(AccountType.DEALER.resolveMaxOffersCount());
    dealerAccount.setOwner(owner);
    dealerAccount.setAccountType(AccountType.DEALER);
    dealerAccount.setEnabled(Boolean.TRUE);
    Address address = modelMapper.map(model.getAddress(), Address.class);
    address.setLocation(location);
    address.setLocation(location);
    address.setResident(dealerAccount);
    dealerAccount.setAddress(address);
    accountRepository.saveAndFlush(dealerAccount);
    owner.setHasAccount(Boolean.TRUE);
    userRepository.save(owner);
    logAccountCreate(AccountType.DEALER, owner);
    // TODO notify admin when created
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
    private Long addressLocationId;
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
    userLogger.logUserAddPrivateAccount(owner.getId());
  }
}
