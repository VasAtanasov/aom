package bg.autohouse.service.services;

import bg.autohouse.data.models.User;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.user.AuthorizedUserServiceModel;
import bg.autohouse.web.models.request.UserDetailsUpdateRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  UserDetails loadUserById(UUID id);

  String generateUserRegistrationVerifier(UserRegisterServiceModel model);

  UserServiceModel completeRegistration(String username);

  UserServiceModel updateUser(UUID userId, UserDetailsUpdateRequest user, User loggedUser);

  boolean userExist(String username);

  boolean requestExists(String username);

  String regenerateUserVerifier(String username);

  void updateHasImage(UUID id, boolean b);

  AuthorizedUserServiceModel tryLogin(String username, String password);

  List<UUID> addToFavorites(UUID id, UUID offerId);
}
