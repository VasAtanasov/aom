package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.account.AccountLog;
import bg.autohouse.data.models.enums.AccountLogType;
import bg.autohouse.data.models.enums.SellerType;
import bg.autohouse.data.repositories.AccountLogRepository;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.service.models.AccountServiceModel;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.ModelMapperWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountServiceImpl implements AccountService {
  enum AccountType {
    PRIVATE_ACCOUNT,
    DEALER_ACCOUNT;
  }

  private static final int MAX_PRIVATE_ACCOUNT_OFFERS = 5;
  // private static final int MAX_DEALER_ACCOUNT_OFFERS = 200;

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final AccountLogRepository accountLogRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
  @Transactional(readOnly = true)
  public boolean isHasAccount(String id) {
    return accountRepository.existsByOwnerId(id);
  }

  @Override
  public AccountServiceModel loadAccountForUser(String userId) {
    Assert.notNull(userId, "User id is required.");
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException(ExceptionsMessages.USER_NOT_FOUND_ID));

    if (!user.isHasAccount()) return null;

    Account account =
        accountRepository
            .findByOwnerId(userId)
            .orElseThrow(() -> new NotFoundException(ExceptionsMessages.ACCOUNT_NOT_FOUND));

    return modelMapper.map(account, AccountServiceModel.class);
  }

  @Override
  @Transactional
  public void createPrivateSellerAccount(AccountServiceModel model, String ownerId) {
    Assert.notNull(model, "No account model found");
    Assert.notNull(ownerId, "Account owner must be provided");

    User owner =
        userRepository
            .findById(ownerId)
            .orElseThrow(() -> new NotFoundException(ExceptionsMessages.USER_NOT_FOUND_ID));

    Account privateAccount = modelMapper.map(model, Account.class);
    privateAccount.setMaxOffersCount(MAX_PRIVATE_ACCOUNT_OFFERS);
    privateAccount.setOwner(owner);
    privateAccount.setEnabled(Boolean.TRUE);
    accountRepository.save(privateAccount);
    owner.setHasAccount(Boolean.TRUE);
    owner.setSellerType(SellerType.PRIVATE);
    userRepository.save(owner);

    AccountLog accountLogCreate =
        AccountLog.builder()
            .accountLogType(AccountLogType.ACCOUNT_CREATED)
            .description(AccountType.PRIVATE_ACCOUNT.name())
            .user(owner)
            .build();

    AccountLog accountLogEnable =
        AccountLog.builder()
            .accountLogType(AccountLogType.ACCOUNT_ENABLED)
            .description(AccountType.PRIVATE_ACCOUNT.name())
            .user(owner)
            .build();

    accountLogRepository.save(accountLogCreate);
    accountLogRepository.save(accountLogEnable);
  }

  // @Override
  // public DealershipServiceModel registerDealer(String userId,
  // DealershipServiceModel dealer) {

  // if (existsByDealershipName(dealer.getName())) {
  // throw new ResourceAlreadyExistsException(
  // String.format(ExceptionsMessages.DEALERSHIP_ALREADY_EXISTS,
  // dealer.getName()));
  // }
  // Address address =
  // addressRepository
  // .findById(dealer.getLocationId())
  // .orElseThrow(() -> new
  // NotFoundException(ExceptionsMessages.INVALID_LOCATION));
  // User user =
  // userRepository
  // .findById(userId)
  // .orElseThrow(
  // () ->
  // new
  // UsernameNotFoundException(ExceptionsMessages.EXCEPTION_USER_NOT_FOUND_ID));

  // Dealership dealership = modelMapper.map(dealer, Dealership.class);
  // dealership.setDealer(user);
  // dealershipRepository.save(dealership);
  // log.info("Saved dealership with name: {}", dealership.getName());
  // return modelMapper.map(dealership, DealershipServiceModel.class);

  // return null;
  // }
}
