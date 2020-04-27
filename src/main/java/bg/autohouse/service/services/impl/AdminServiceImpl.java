package bg.autohouse.service.services.impl;

import static bg.autohouse.data.specifications.UserSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.*;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.geo.Address;
import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.projections.user.UserIdUsername;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.account.AccountCreateServiceModel;
import bg.autohouse.service.models.account.AccountServiceModel;
import bg.autohouse.service.services.AdminService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.Collect;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.F;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.StringGenericUtils;
import bg.autohouse.web.enums.RestMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminServiceImpl implements AdminService {

  @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:50}")
  private int batchSize;

  private final UserRepository userRepository;
  private final LocationRepository locationRepository;
  private final ModelMapperWrapper modelMapper;
  private final AccountRepository accountRepository;

  @Override
  public List<UserIdUsername> loadAllUsers() {
    return userRepository.getAllUsers();
  }

  // TODO not encoding password its degregating performance of application for batch
  @Override
  @Transactional
  public List<UserServiceModel> bulkRegisterUsers(UUID adminId, List<String> usernames) {
    Assert.notNull(adminId, "Admin id is required");
    Assert.notNull(usernames, "Invalid usernames collection");
    validateAdminRole(adminId);
    Set<String> existingUsers =
        F.mapNonNullsToSet(
            userRepository.getAllUsers(usernameIn(usernames)), user -> user.getUsername());
    List<User> users = new ArrayList<>();
    List<User> registered = new ArrayList<>();
    long startNanos = System.nanoTime();
    for (int i = 0; i < usernames.size(); i++) {
      String email = usernames.get(i);
      if (existingUsers.contains(email)) continue;
      String password = StringGenericUtils.nextPassword(8);
      User user = User.createNormalUser(email, password);
      users.add(user);
      if (i % batchSize == 0 && i > 0) {
        log.info("Saving {} entities ...", users.size());
        long startSaveAll = System.nanoTime();
        registered.addAll(userRepository.saveAll(users));
        log.info(
            "{}.bulkRegisterUsers saving batch of {} took {} millis",
            getClass().getSimpleName(),
            users.size(),
            TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startSaveAll));
        users.clear();
      }
    }
    if (users.size() > 0) {
      log.info("Saving the remaining {} entities ...", users.size());
      registered.addAll(userRepository.saveAll(users));
      users.clear();
    }
    log.info(
        "{}.bulkRegisterUsers took {} millis",
        getClass().getSimpleName(),
        TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
    return modelMapper.mapAll(registered, UserServiceModel.class);
  }

  @Override
  public int bulkCreateAccounts(UUID adminId, List<AccountCreateServiceModel> models) {
    Assert.notNull(adminId, "Admin id is required");
    Assert.notNull(models, "Invalid accounts collection");
    validateAdminRole(adminId);
    List<UserServiceModel> users = bulkCreateAccountsInternally(models);
    return updateHasAccount(F.mapNonNullsToList(users, u -> u.getId()));
  }

  @Transactional
  private List<UserServiceModel> bulkCreateAccountsInternally(
      List<AccountCreateServiceModel> models) {
    List<UUID> ids = F.mapNonNullsToList(models, m -> m.getId());
    List<String> usernames = F.mapNonNullsToList(models, m -> m.getUsername());
    Map<UUID, User> usersById =
        userRepository.getAllMap(where(hasNoAccount()).and(idIn(ids)).and(usernameIn(usernames)));
    Map<Long, Location> locationsById =
        locationRepository.findAll().stream().collect(Collect.indexingBy(l -> l.getId()));
    List<Account> accounts = new ArrayList<>();
    for (int i = 0; i < models.size(); i++) {
      AccountCreateServiceModel model = models.get(i);
      UUID userId = model.getId();
      if (!usersById.containsKey(userId)) continue;
      User owner = usersById.getOrDefault(userId, null);
      if (owner == null) continue;
      String username = model.getUsername();
      if (!owner.getUsername().equals(username)) continue;
      AccountServiceModel accountModel = model.getAccount();
      Assert.notNull(accountModel.getAccountType(), "Invalid AccountType");
      AccountType accountType =
          EnumUtils.fromString(accountModel.getAccountType(), AccountType.class)
              .orElseThrow(
                  () -> new IllegalStateException(RestMessage.INVALID_ACCOUNT_TYPE.name()));
      Account account;
      if (AccountType.DEALER.equals(accountType)) {
        account = Account.createDealerAccount(accountModel, owner);
        String street = accountModel.getAddress().getStreet();
        Location location = locationsById.get(accountModel.getAddress().getLocationId());
        Address.createAddress(location, street, account);
      } else {
        account = Account.createPrivateAccount(accountModel, owner);
      }
      accounts.add(account);
      if (i % batchSize == 0 && i > 0) {
        accountRepository.saveAll(accounts);
        accounts.clear();
      }
    }
    if (accounts.size() > 0) {
      accountRepository.saveAll(accounts);
      accounts.clear();
    }
    return modelMapper.mapAll(
        F.filterToStream(usersById.values(), u -> u.isHasAccount()), UserServiceModel.class);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  private int updateHasAccount(List<UUID> ids) {
    return userRepository.updateHasAccount(ids);
  }

  private void validateAdminRole(UUID id) {
    User admin = userRepository.findByIdWithRoles(id).orElseThrow(NoSuchUserException::new);
    if (!admin.isAdmin()) {
      throw new AccessDeniedException("Error! User does not have admin role");
    }
  }
}
