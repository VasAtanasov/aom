package bg.autohouse.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.config.properties.SecurityConfigurationProperties;
import bg.autohouse.data.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtTokenServiceImplTest {
  @Autowired private JwtTokenRepository tokenRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private JwtTokenService tokenService;
  @Autowired private SecurityConfigurationProperties props;

  // @BeforeEach
  // void init() {
  //   log.info("Jwt secret: {}", new String(props.getJwtSecret()));
  //   log.info("Jwt expiration: {}", props.getExpirationTime());
  //   log.info("Jwt password reset expiration: {}", props.getPasswordRestExpirationTime());
  //   List<User> users = userRepository.findAllWithRoles();
  //   List<JwtToken> tokens =
  //       users.stream()
  //           .map(
  //               user ->
  //                   tokenService.createJwtEntity(
  //                       new JwtTokenCreateRequest(JwtTokenType.VALIDATION, user)))
  //           .collect(Collectors.toList());
  //   tokenRepository.saveAll(tokens);
  // }

  @Test
  void whenFindToken_existingUser_ShouldReturnNonNull() {
    JwtToken tokenEntity =
        tokenRepository.findOne(JwtTokenSpecifications.forUser("vas")).orElse(null);
    assertThat(tokenEntity).isNull();
  }
}
