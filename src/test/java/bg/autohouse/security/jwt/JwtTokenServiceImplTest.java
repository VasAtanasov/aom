package bg.autohouse.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtTokenServiceImplTest {
  @Autowired private UserRepository userRepository;
  @Autowired private JwtTokenService tokenService;
  private User user;
  private JwtTokenCreateRequest tokenRequest;
  private String token;

  @BeforeEach
  void initUser() {
    user = userRepository.findByUsernameIgnoreCase(DatabaseSeeder.ROOT_USERNAME).orElse(null);
    assertThat(user).isNotNull();
    tokenRequest = new JwtTokenCreateRequest(JwtTokenType.API_CLIENT, user);
    token = tokenService.createJwt(tokenRequest);
  }

  @Test
  void when_isJwtTokenValid_isTrue() {
    boolean isValid = tokenService.isJwtTokenValid(token);
    assertThat(isValid).isTrue();
  }

  @Test
  void when_isJwtTokenExpired_isFalse() {
    boolean isValid = tokenService.isJwtTokenExpired(token);
    assertThat(isValid).isFalse();
  }

  @Test
  void when_blackListJwt_shouldSaveToBlackList() {
    tokenService.blackListJwt(token);
    boolean isBlackListed = tokenService.isBlackListed(token);
    assertThat(isBlackListed).isTrue();
  }

  @Test
  void when_refreshToken_withBlackListedTone_shouldReturnNull() {
    tokenService.blackListJwt(token);
    boolean isBlackListed = tokenService.isBlackListed(token);
    assertThat(isBlackListed).isTrue();
    String refreshedToken = tokenService.refreshToken(token, JwtTokenType.API_CLIENT);
    assertThat(refreshedToken).isNull();
  }
}
