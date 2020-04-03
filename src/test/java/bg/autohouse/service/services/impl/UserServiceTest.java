package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.enums.Seller;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.security.jwt.JwtAuthenticationTokenProvider;
import bg.autohouse.security.jwt.JwtAuthenticationTokenRepository;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.services.EmailService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.ModelMapperWrapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
  private static final UserRegisterServiceModel VALID_REGISTER_MODEL =
      UserRegisterServiceModel.builder()
          .username("username")
          .password("password")
          .phoneNumber("phoneNumber")
          // .email("email")
          .firstName("firstName")
          .lastName("lastName")
          .seller(Seller.PRIVATE.name())
          .build();

  private UserRepository userRepository;
  private PasswordEncoder encoder;
  private UserService userService;
  private EmailService emailService;
  private JwtAuthenticationTokenRepository tokenRepository;

  private JwtAuthenticationTokenProvider tokenProvider;
  private ModelMapperWrapper modelMapper =
      new ModelMapperWrapperImpl(SingletonModelMapper.mapper());

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    encoder = mock(PasswordEncoder.class);
    emailService = mock(EmailService.class);
    tokenProvider = mock(JwtAuthenticationTokenProvider.class);
    tokenRepository = mock(JwtAuthenticationTokenRepository.class);
    userService =
        new UserServiceImpl(
            userRepository, modelMapper, encoder, tokenProvider, tokenRepository, emailService);
  }

  @Test
  void whenExistsByUsername_whenTrue_shouldThrow() {

    when(userService.existsByUsername(anyString())).thenReturn(Boolean.TRUE);

    Throwable thrown =
        catchThrowable(
            () -> {
              userService.register(VALID_REGISTER_MODEL);
            });
    String message =
        String.format(
            ExceptionsMessages.USERNAME_ALREADY_EXISTS, VALID_REGISTER_MODEL.getUsername());
    assertThat(thrown).isInstanceOf(ResourceAlreadyExistsException.class).hasMessage(message);
  }

  @Test
  void whenExistsByEmail_whenTrue_shouldThrow() {

    when(userService.existsByUsername(anyString())).thenReturn(Boolean.TRUE);

    Throwable thrown =
        catchThrowable(
            () -> {
              userService.register(VALID_REGISTER_MODEL);
            });
    String message =
        String.format(ExceptionsMessages.EMAIL_ALREADY_EXISTS, VALID_REGISTER_MODEL.getUsername());
    assertThat(thrown).isInstanceOf(ResourceAlreadyExistsException.class).hasMessage(message);
  }
}
