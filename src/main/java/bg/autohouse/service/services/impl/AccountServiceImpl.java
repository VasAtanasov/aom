package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.account.AccountLog;
import bg.autohouse.data.models.enums.AccountLogType;
import bg.autohouse.data.models.enums.AccountType;
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

  // TODO return account details
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
    privateAccount.setMaxOffersCount(AccountType.PRIVATE.resolveMaxOffersCount());
    privateAccount.setOwner(owner);
    privateAccount.setAccountType(AccountType.PRIVATE);
    accountRepository.save(privateAccount);
    owner.setHasAccount(Boolean.TRUE);
    userRepository.save(owner);

    AccountLog accountLogCreate =
        AccountLog.builder()
            .accountLogType(AccountLogType.ACCOUNT_CREATED)
            .description(AccountType.PRIVATE.name())
            .user(owner)
            .build();

    accountLogRepository.save(accountLogCreate);
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
