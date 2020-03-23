package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.enums.Seller;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.ModelMapperWrapperImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
  private UserRepository userRepository;
  private PasswordEncoder encoder;
  private UserService userService;
  private ModelMapperWrapper modelMapper =
      new ModelMapperWrapperImpl(SingletonModelMapper.mapper());

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    encoder = mock(PasswordEncoder.class);
    userService = new UserServiceImpl(userRepository, modelMapper, encoder);
  }

  @Test
  void whenRegister_validData_shouldRegister() {

    UserRegisterServiceModel model =
        UserRegisterServiceModel.builder()
            .username("username")
            .password("password")
            .phoneNumber("phoneNumber")
            .email("email")
            .firstName("firstName")
            .lastName("lastName")
            .seller(Seller.PRIVATE.name())
            .build();

    UserServiceModel userServiceModel = userService.register(model);
    Optional<Seller> enumValue = EnumUtils.fromString(userServiceModel.getSeller(), Seller.class);

    assertThat(enumValue).isPresent();
  }
}
