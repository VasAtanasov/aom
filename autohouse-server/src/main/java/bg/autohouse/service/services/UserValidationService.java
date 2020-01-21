package bg.autohouse.service.services;

public interface UserValidationService {
    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);
}
