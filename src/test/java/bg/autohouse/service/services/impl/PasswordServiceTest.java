package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.security.jwt.JwtToken;
import bg.autohouse.service.services.PasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@Transactional
public class PasswordServiceTest {

  @Autowired private PasswordService passwordService;

  @Test
  void when_generateRegistrationToken_nonExisting_shouldReturnToken() {
    JwtToken token = passwordService.generateRegistrationToken("vas");
    assertThat(token.getUserId()).isNull();
    assertThat(token.getValue()).isNotNull();
  }
}
