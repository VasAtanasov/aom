package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.data.models.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRequestRepositoryTest {
  private static final UserCreateRequest VALID_REQUEST =
      UserCreateRequest.builder().username("username@mail.com").password("password").build();

  @Autowired private UserRequestRepository userRequestRepository;

  @Test
  void when_isVerified_false_shouldReturn() {
    userRequestRepository.save(VALID_REQUEST);

    UserCreateRequest request =
        userRequestRepository
            .findByUsernameAndIsVerifiedIsFalse(VALID_REQUEST.getUsername())
            .orElse(null);

    assertThat(request).isNotNull();
  }

  @Test
  void when_isVerified_true_shouldReturnNull() {
    UserCreateRequest new_request =
        UserCreateRequest.builder()
            .username("username@mail.com")
            .password("password")
            .isVerified(true)
            .build();

    userRequestRepository.save(new_request);

    UserCreateRequest request =
        userRequestRepository
            .findByUsernameAndIsVerifiedIsFalse(VALID_REQUEST.getUsername())
            .orElse(null);

    assertThat(request).isNull();
  }
}
