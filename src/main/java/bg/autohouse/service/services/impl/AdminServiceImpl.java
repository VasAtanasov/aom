package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.projections.user.UserIdUsername;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.service.services.AdminService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.StringGenericUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminServiceImpl implements AdminService {

  @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:50}")
  private int batchSize;

  private final UserRepository userRepository;

  @Override
  @Transactional
  public void bulkRegisterUsers(UUID adminId, List<String> usernames) {
    Assert.notNull(adminId, "Admin id is required");
    Assert.notNull(usernames, "Invalid usernames collection");
    validateAdminRole(adminId);
    List<User> users = new ArrayList<>();
    long startNanos = System.nanoTime();
    for (int i = 0; i < usernames.size(); i++) {
      String email = usernames.get(i);
      String password = StringGenericUtils.nextPassword(8);
      // TODO not encoding password because its degregating performance of application for batch
      // records
      // long startEncoding = System.nanoTime();
      // String encodedPassword = encoder.encode(password);
      // log.info(
      //     "Generating/Encoding password for {} millis",
      //     TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startEncoding));
      User user = User.createNormalUser(email, password);
      users.add(user);
      if (i % batchSize == 0 && i > 0) {
        log.info("Saving {} entities ...", users.size());
        long startSaveAll = System.nanoTime();
        userRepository.saveAll(users);
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
      userRepository.saveAll(users);
      users.clear();
    }
    log.info(
        "{}.bulkRegisterUsers took {} millis",
        getClass().getSimpleName(),
        TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
    // TODO add accounts to users
  }

  private void validateAdminRole(UUID id) {
    User admin = userRepository.findById(id).orElseThrow(NoSuchUserException::new);
    if (!admin.isAdmin()) {
      throw new AccessDeniedException("Error! User does not have admin role");
    }
  }

  @Override
  public List<UserIdUsername> loadAllUsers() {
    return userRepository.getAllUsers();
  }
}
