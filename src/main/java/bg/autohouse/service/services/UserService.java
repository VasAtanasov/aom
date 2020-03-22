package bg.autohouse.service.services;

import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  UserDetails loadUserById(String id);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  UserServiceModel register(UserRegisterServiceModel model);
}
