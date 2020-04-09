package bg.autohouse.service.services;

import bg.autohouse.data.models.User;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.web.models.request.UserDetailsUpdateRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  UserDetails loadUserById(String id);

  boolean existsByUsername(String username);

  UserServiceModel register(UserRegisterServiceModel model);

  UserServiceModel updateUser(String userId, UserDetailsUpdateRequest user, User loggedUser);

  boolean verifyEmailToken(String token);

  boolean requestPasswordReset(String username);

  boolean resetPassword(String token, String password);

  boolean userExist(String username);
}
