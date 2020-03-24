package bg.autohouse.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.config.properties.SecurityConfigurationProperties;
import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql("/data.sql")
@Import(value = {JwtAuthenticationTokenProvider.class, SecurityConfigurationProperties.class})
@TestPropertySource("classpath:test.properties")
public class JwtAuthenticationTokenProviderTest {
  @Autowired private JwtAuthenticationTokenRepository tokenRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private JwtAuthenticationTokenProvider tokenProvider;
  @Autowired private SecurityConfigurationProperties props;

  @BeforeEach
  void init() {
    log.info("{}", new String(props.getJwtSecret()));
    List<User> users = userRepository.findAllWithRoles();
    List<JwtAuthenticationToken> tokens =
        users.stream()
            .map(u -> tokenProvider.generateTokenEntity(u, JwtAuthenticationTokenType.VALIDATION))
            .collect(Collectors.toList());
    tokenRepository.saveAll(tokens);
  }

  @Test
  void whenCount_shouldNotBeEmpty() {
    assertThat(tokenRepository.count()).isGreaterThan(0L);
  }
}
