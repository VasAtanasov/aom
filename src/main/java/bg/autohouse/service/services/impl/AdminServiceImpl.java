package bg.autohouse.service.services.impl;

import static bg.autohouse.data.specifications.UserSpecifications.*;
import static bg.autohouse.service.services.impl.OfferServiceImpl.*;
import static org.springframework.data.jpa.domain.Specification.*;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.models.geo.Address;
import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.projections.user.UserUsername;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.AccountNotFoundException;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.errors.RoleChangeException;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.account.AccountCreateServiceModel;
import bg.autohouse.service.models.account.AccountServiceModel;
import bg.autohouse.service.models.user.ChangeRoleServiceModel;
import bg.autohouse.service.models.user.UserAdminDetailsServiceModel;
import bg.autohouse.service.models.user.UserRowServiceModel;
import bg.autohouse.service.services.AdminService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.F;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.StringGenericUtils;
import bg.autohouse.web.enums.RestMessage;

import java.util.*;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  private static final long JVM_STARTUP_TIMESTAMP = System.currentTimeMillis();

  private final UserRepository userRepository;
  private final UserService userService;
  private final LocationRepository locationRepository;
  private final ModelMapperWrapper modelMapper;
  private final AccountRepository accountRepository;

  @Override
  public String getRevision() {
    return Long.toHexString(JVM_STARTUP_TIMESTAMP);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<UserRowServiceModel> loadUsersPage(Pageable pageable) {
    Page<User> users = userRepository.findUsersPage(pageable);
    return users.map(UserRowServiceModel::new);
  }

  @Override
  @Transactional(readOnly = true)
  public UserAdminDetailsServiceModel loadUserDetails(UUID userId, UUID adminId) {
    validateAdminRole(adminId);
    User targetUser =
        userRepository.findByIdWithRoles(userId).orElseThrow(NoSuchUserException::new);
    AccountServiceModel accountServiceModel = null;
    if (targetUser.isHasAccount()) {
      Account account =
          accountRepository.findByUserId(userId).orElseThrow(AccountNotFoundException::new);
      accountServiceModel = modelMapper.map(account, AccountServiceModel.class);
    }
    return UserAdminDetailsServiceModel.builder()
        .user(targetUser)
        .account(accountServiceModel)
        .build();
  }

  @Override
  @Transactional
  public UserRowServiceModel changeRole(ChangeRoleServiceModel request, User user) {
    if (!Assert.has(request.getNewRole()) || !Assert.has(request.getCurrentRole())) {
      throw new RoleChangeException();
    }
    if (!EnumUtils.has(request.getNewRole(), Role.class)
        || !EnumUtils.has(request.getCurrentRole(), Role.class)) {
      throw new RoleChangeException();
    }
    if (request.getNewRole().equals(Role.ROOT) || request.getCurrentRole().equals(Role.ROOT)) {
      throw new RoleChangeException();
    }
    validateAdminRole(user.getId());
    User affectedUser =
        userRepository.findByIdWithRoles(request.getUserId()).orElseThrow(NoSuchUserException::new);
    affectedUser.setRoles(userService.getInheritedRolesFromRole(request.getNewRole()));
    return new UserRowServiceModel(userRepository.save(affectedUser));
  }

  @Override
  @Transactional
  @Caching(
      evict = {
        @CacheEvict(value = LATEST_OFFERS_CACHE, allEntries = true),
        @CacheEvict(value = FILTERED_ACTIVE_OFFERS, allEntries = true),
        @CacheEvict(value = USER_OFFERS_CACHE, allEntries = true),
        @CacheEvict(value = OFFERS_BY_IDS_CACHE, allEntries = true),
      })
  public boolean toggleActive(UUID userId, UUID adminId) {
    validateAdminRole(adminId);
    User targetUser =
        userRepository.findByIdWithRoles(userId).orElseThrow(NoSuchUserException::new);
    targetUser.toggleActive();
    return targetUser.isEnabled();
  }

  // TODO not encoding password its deteriorate performance of application for batch
  @Override
  @Transactional
  public List<UserServiceModel> bulkRegisterUsers(UUID adminId, List<String> usernames) {
    Assert.notNull(adminId, "Admin id is required");
    Assert.notNull(usernames, "Invalid usernames collection");
    validateAdminRole(adminId);
    Set<String> existingUsers =
        F.mapNonNullsToSet(
            userRepository.getAllUsers(usernameIn(usernames)), UserUsername::getUsername);
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
    return updateHasAccount(F.mapNonNullsToList(users, UserServiceModel::getId));
  }

  @Transactional
  List<UserServiceModel> bulkCreateAccountsInternally(List<AccountCreateServiceModel> models) {
    List<UUID> ids = F.mapNonNullsToList(models, AccountCreateServiceModel::getId);
    List<String> usernames = F.mapNonNullsToList(models, AccountCreateServiceModel::getUsername);
    Map<UUID, User> usersById =
        userRepository.getAllMap(
            Objects.requireNonNull(where(hasNoAccount()).and(idIn(ids)))
                .and(usernameIn(usernames)));
    List<Location> locations = locationRepository.findAllLocations();
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
        Location location =
            locations.stream()
                .filter(
                    l ->
                        l.getPostalCode().equals(accountModel.getAddress().getLocationPostalCode()))
                .findFirst()
                .orElse(locations.get(0));
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
        F.filterToStream(usersById.values(), User::isHasAccount), UserServiceModel.class);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  int updateHasAccount(List<UUID> ids) {
    return userRepository.updateHasAccount(ids);
  }

  private void validateAdminRole(UUID id) {
    User admin = userRepository.findByIdWithRoles(id).orElseThrow(NoSuchUserException::new);
    if (!admin.isAdmin()) {
      throw new AccessDeniedException("Error! User does not have admin role");
    }
  }
}
