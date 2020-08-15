package bg.autohouse.service.services;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.user.AuthorizedUserServiceModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService extends UserDetailsService {
  UserDetails loadUserById(UUID id);

  User fetchUserByUsername(String username);

  String generateUserRegistrationVerifier(UserRegisterServiceModel model);

  UserServiceModel completeRegistration(String username);

  boolean userExist(String username);

  String regenerateUserVerifier(String username);

  AuthorizedUserServiceModel tryLogin(String username, String password);

  List<UUID> addToFavorites(UUID id, UUID offerId);

  Set<Role> getInheritedRolesFromRole(Role role);
}
