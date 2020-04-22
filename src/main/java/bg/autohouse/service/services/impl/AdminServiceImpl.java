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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminServiceImpl implements AdminService {

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
    userRepository.saveAll(usersToRegister);
  }

  private void validateAdminRole(String id) {
    User admin = userRepository.findById(id).orElseThrow(NoSuchUserException::new);
    if (!admin.isAdmin()) {
      throw new AccessDeniedException("Error! User does not have admin role");
    }
  }
}
