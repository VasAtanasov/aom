package bg.autohouse.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.config.properties.SecurityConfigurationProperties;
import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.ExceptionsMessages;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@Transactional
@Sql("/data.sql")
@TestPropertySource("classpath:test.properties")
public class JwtAuthenticationTokenProviderTest {
  @Autowired private JwtAuthenticationTokenRepository tokenRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private JwtAuthenticationTokenProvider tokenProvider;
  @Autowired private SecurityConfigurationProperties props;

  @BeforeEach
  void init() {
    log.info("Jwt secret: {}", new String(props.getJwtSecret()));
    log.info("Jwt expiration: {}", props.getExpirationTime());
    log.info("Jwt password reset expiration: {}", props.getPasswordRestExpirationTime());
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

  @Test
  void whenFindToken_existingUser_ShouldReturnNonNull() {
    JwtAuthenticationToken tokenEntity =
        tokenRepository
            .findOne(JwtAuthenticationTokenSpecifications.forUser("vas"))
            .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.INVALID_TOKEN));
    assertThat(tokenEntity).isNotNull();
  }
}
