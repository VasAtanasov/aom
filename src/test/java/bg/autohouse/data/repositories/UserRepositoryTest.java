package bg.autohouse.data.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.utils.TimingExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith({SpringExtension.class, TimingExtension.class})
@DataJpaTest
@ActiveProfiles("test")
@Sql("/data/users.sql")
public class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Test
  void when_isVerified_false_shouldReturn() {
    long count = userRepository.count();
    log.info("Total users count: {}", count);
    assertThat(count).isGreaterThan(1000L);
  }
}
