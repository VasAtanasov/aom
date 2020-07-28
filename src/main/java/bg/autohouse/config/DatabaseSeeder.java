package bg.autohouse.config;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.repositories.UserRepository;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DatabaseSeeder {
  public static final String ROOT_USERNAME = "vas@mail.com";
  public static final String USERNAME = "simple_user@mail.com";
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @PostConstruct
  public void seedRootUser() {
    if (!userRepository.existsByUsernameIgnoreCase(ROOT_USERNAME)) {
      Set<Role> roles = new HashSet<>();
      roles.add(Role.ROOT);
      roles.add(Role.ADMIN);
      roles.add(Role.USER);
      User user = new User();
      user.setUsername(ROOT_USERNAME);
      user.setPassword(encoder.encode("123"));
      user.setRoles(roles);
      userRepository.save(user);
    }
  }

  @PostConstruct
  public void seedUser() {
    if (!userRepository.existsByUsernameIgnoreCase(USERNAME)) {
      Set<Role> roles = new HashSet<>();
      roles.add(Role.USER);
      User user = new User();
      user.setUsername(USERNAME);
      user.setPassword(encoder.encode("123"));
      user.setRoles(roles);
      userRepository.save(user);
    }
  }
}
