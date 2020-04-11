package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.security.jwt.JwtToken;
import bg.autohouse.service.services.PasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PasswordServiceTest {

  @Autowired private PasswordService passwordService;

  @Test
  void when_generateRegistrationToken_nonExisting_shouldReturnToken() {
    JwtToken token = passwordService.generateRegistrationToken("vas");
    assertThat(token.getUserId()).isNull();
    assertThat(token.getValue()).isNotNull();
  }
}
