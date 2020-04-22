package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.service.services.AdminService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.F;
import bg.autohouse.util.StringGenericUtils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminServiceImpl implements AdminService {

  @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
  private int batchSize;

  @PersistenceContext private EntityManager entityManager;

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Override
  @Transactional
  public void bulkRegisterUsers(String adminId, List<String> usernames) {
    Assert.notNull(adminId, "Admin id is required");
    Assert.notNull(usernames, "Invalid usernames collection");
    validateAdminRole(adminId);
    List<User> usersToRegister =
        F.mapNonNullsToList(
            usernames,
            email -> {
              User user = new User();
              user.setUsername(email);
              String password = StringGenericUtils.nextPassword(12);
              user.setPassword(encoder.encode(password));
              user.getRoles().add(Role.USER);
              return user;
            });
    userRepository.saveInBatch(usersToRegister);
    // userRepository.saveAll(usersToRegister);
    // List<User> users = new ArrayList<>();
    // for (int i = 0; i < usernames.size(); i++) {
    //   String email = usernames.get(i);
    //   User user = new User();
    //   user.setUsername(email);
    //   String password = StringGenericUtils.nextPassword(12);
    //   user.setPassword(encoder.encode(password));
    //   user.getRoles().add(Role.USER);
    //   users.add(user);
    // if (i % 2000 == 0 && i > 0) {
    //   userRepository.saveAll(users);
    //   // userRepository.flush();
    //   users.clear();
    // }
    // }
    // authorRepository.saveInBatch(authors);
    // if (users.size() > 0) {
    //   userRepository.saveAll(users);
    //   // userRepository.flush();
    //   users.clear();
    // }
  }

  private void validateAdminRole(String id) {
    User admin = userRepository.findById(id).orElseThrow(NoSuchUserException::new);
    if (!admin.isAdmin()) {
      throw new AccessDeniedException("Error! User does not have admin role");
    }
  }

  // public <S extends User> void saveInBatch(Iterable<S> entities) {

  //   if (entities == null) {
  //     throw new IllegalArgumentException("The given Iterable of entities not be null!");
  //   }

  //   int i = 0;

  //   for (S entity : entities) {
  //     entityManager.persist(entity);

  //     i++;

  //     // Flush a batch of inserts and release memory
  //     if (i % batchSize == 0 && i > 0) {
  //       log.info("Flushing the EntityManager containing {0} entities ...", i);

  //       entityManager.flush();
  //       entityManager.clear();
  //       i = 0;
  //     }
  //   }

  //   if (i > 0) {
  //     log.info("Flushing the remaining {0} entities ...", i);

  //     entityManager.flush();
  //     entityManager.clear();
  //   }
  // }
}
