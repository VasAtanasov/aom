package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.UserCreateRequest;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.data.repositories.UserRequestRepository;
import bg.autohouse.errors.NoRegistrationRequestFoundException;
import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.web.enums.RestMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
public class UserServiceImplTest {
  private static final UserRegisterServiceModel VALID_REGISTER_MODEL =
      UserRegisterServiceModel.builder()
          .username("username@mail.com")
          .password("passwordIsVeryStrong")
          .build();

  private static final long ZERO = 0L;

  @Autowired private UserService userService;
  @Autowired private UserRequestRepository userRequestRepository;
  @Autowired private PasswordService passwordService;
  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder encoder;

  @Test
  void when_generateUserRegistrationVerifier_shouldReturnToken() {
    String token = userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);
    assertThat(token).isNotNull();
  }

  @Test
  void when_generateUserRegistrationVerifier_withSameUsername_shouldReturnToken() {
    String token = userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);
    String sameToken = userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);
    assertThat(token).isEqualTo(sameToken);
  }

  @Test
  void when_generateUserRegistrationVerifier_withValidUsername_shouldReturnModel() {
    String token = userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);
    assertThat(passwordService.isShortLivedOtpValid(VALID_REGISTER_MODEL.getUsername(), token))
        .isTrue();
    assertThat(userRequestRepository.count()).isGreaterThan(ZERO);
  }

  @Test
  void when_register_withValidModel_shouldRegister() {
    userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);

    UserCreateRequest request =
        userRequestRepository
            .findByUsernameAndIsVerifiedIsFalse(VALID_REGISTER_MODEL.getUsername())
            .orElse(null);

    assertThat(request).isNotNull();

    UserServiceModel registeredUser =
        userService.completeRegistration(VALID_REGISTER_MODEL.getUsername());

    assertThat(userRepository.count()).isGreaterThan(ZERO);

    User user = userRepository.findByIdWithRoles(registeredUser.getId()).orElse(null);
    assertThat(user).isNotNull();
  }

  @Test
  void when_generateUserRegistrationVerifier_withExistingUser_shouldThrow() {
    userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);

    UserServiceModel registeredUser =
        userService.completeRegistration(VALID_REGISTER_MODEL.getUsername());

    assertThat(userRepository.count()).isGreaterThan(ZERO);
    assertThat(registeredUser).isNotNull();

    Throwable thrown =
        catchThrowable(() -> userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL));

    assertThat(thrown)
        .isInstanceOf(ResourceAlreadyExistsException.class)
        .hasMessage(RestMessage.USER_ALREADY_EXISTS.name());
  }

  @Test
  void when_generateUserRegistrationVerifier_null_shouldThrow() {
    userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);

    Throwable thrown = catchThrowable(() -> userService.completeRegistration(null));

    assertThat(thrown)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("User email address is required");
  }

  @Test
  void when_generateUserRegistrationVerifier_nullUsername_shouldThrow() {
    Throwable thrown = catchThrowable(() -> userService.generateUserRegistrationVerifier(null));

    assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessage("Model is required");
  }

  @Test
  void when_resetPassword_withValidToken_shouldReset() {
    userService.generateUserRegistrationVerifier(VALID_REGISTER_MODEL);
    userService.completeRegistration(VALID_REGISTER_MODEL.getUsername());

    String token = userService.regenerateUserVerifier(VALID_REGISTER_MODEL.getUsername());

    boolean iseReset =
        passwordService.resetPassword(VALID_REGISTER_MODEL.getUsername(), token, "12345");

    User user = userRepository.findByUsernameIgnoreCase(VALID_REGISTER_MODEL.getUsername()).get();
    boolean passwordsMatch = encoder.matches("12345", user.getPassword());

    assertThat(iseReset).isTrue();
    assertThat(passwordsMatch).isTrue();
  }

  @Test
  void when_completeRegistration_nonExistentRequest_shouldThrow() {
    Throwable thrown =
        catchThrowable(() -> userService.completeRegistration(VALID_REGISTER_MODEL.getUsername()));

    assertThat(thrown)
        .isInstanceOf(NoRegistrationRequestFoundException.class)
        .hasMessage(RestMessage.USER_REGISTRATION_REQUEST_NOT_FOUND.name());
  }

  @Test
  void when_completeRegistration_userExists_shouldThrow() {
    Throwable thrown =
        catchThrowable(() -> userService.completeRegistration(DatabaseSeeder.USERNAME));

    assertThat(thrown)
        .isInstanceOf(ResourceAlreadyExistsException.class)
        .hasMessage(RestMessage.USER_ALREADY_EXISTS.name());
  }

  @Test
  void when_regenerateUserVerifier_userNotExists_shouldThrow() {
    Throwable thrown =
        catchThrowable(() -> userService.regenerateUserVerifier("not_existing@mail.com"));

    assertThat(thrown)
        .isInstanceOf(NoSuchUserException.class)
        .hasMessage(RestMessage.USER_NOT_FOUND.name());
  }
}
