package bg.autohouse.service.services;

import bg.autohouse.service.models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel findUserByUsername(String username);

    UserServiceModel findUserByEmail(String email);

    void registerUser(UserServiceModel serviceModel);
}
