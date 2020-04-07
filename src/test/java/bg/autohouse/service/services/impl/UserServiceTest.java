package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.enums.SellerType;
import bg.autohouse.data.repositories.AddressRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.security.jwt.JwtAuthenticationTokenProvider;
import bg.autohouse.security.jwt.JwtAuthenticationTokenRepository;
import bg.autohouse.service.models.RegisterServiceModel;
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
          .firstName("firstName")
          .lastName("lastName")
          .seller(SellerType.PRIVATE.name())
          .build();

  private UserRepository userRepository;
  private PasswordEncoder encoder;
  private UserService userService;
  private EmailService emailService;
  private JwtAuthenticationTokenRepository tokenRepository;
  private AddressRepository addressRepository;

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
    addressRepository = mock(AddressRepository.class);
    userService =
        new UserServiceImpl(
            userRepository,
            addressRepository,
            modelMapper,
            encoder,
            tokenProvider,
            tokenRepository,
            emailService);
  }

  @Test
  void whenExistsByUsername_whenTrue_shouldThrow() {

    when(userService.existsByUsername(anyString())).thenReturn(Boolean.TRUE);
    RegisterServiceModel registerServiceModel =
        RegisterServiceModel.builder().user(VALID_REGISTER_MODEL).build();

    Throwable thrown =
        catchThrowable(
            () -> {
              userService.register(registerServiceModel);
            });
    String message =
        String.format(
            ExceptionsMessages.USERNAME_ALREADY_EXISTS, VALID_REGISTER_MODEL.getUsername());
    assertThat(thrown).isInstanceOf(ResourceAlreadyExistsException.class).hasMessage(message);
  }
}
