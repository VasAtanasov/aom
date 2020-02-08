package bg.autohouse.service.services.impl;

import bg.autohouse.common.Constants;
import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.UserAlreadyExistException;
import bg.autohouse.service.services.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;

@Service
@Transient
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserValidationServiceImpl implements UserValidationService {

    private final UserRepository userRepository;

    @Override
    public boolean isUsernameTaken(String username) {
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user != null) {
            throw new UserAlreadyExistException(Constants.EXCEPTION_USERNAME_EXISTS);
        }

        return false;
    }

    @Override
    public boolean isEmailTaken(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user != null) {
            throw new UserAlreadyExistException(Constants.EXCEPTION_EMAIL_EXISTS);
        }

        return false;
    }
}
