package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.VerificationTokenCode;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.service.services.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PasswordServiceTest {
  private static final String VALID_USERNAME = DatabaseSeeder.ROOT_USERNAME;
  private static final String VALID_PASSWORD = "123";

  @Autowired private PasswordService passwordService;
  @Autowired private UserRepository userRepository;

  @Test
  void when_generateRegistrationToken_nonExisting_shouldReturnToken() {
    VerificationTokenCode token = passwordService.generateShortLivedOTP(VALID_USERNAME);
    assertThat(token).isNotNull();
  }

  @Test
  void when_validateCredentials_withValidCredentials_isTru() {
    boolean isValidCredentials =
        passwordService.validateCredentials(VALID_USERNAME, VALID_PASSWORD);
    assertThat(isValidCredentials).isTrue();
  }

  @Test
  public void when_generateShortLivedOTP_notExpired_shouldReturnSame() {
    VerificationTokenCode verificationTokenCode =
        passwordService.generateShortLivedOTP(VALID_USERNAME);

    log.info("Generated Code: {}", verificationTokenCode.getCode());
    VerificationTokenCode verificationTokenCodeOther =
        passwordService.generateShortLivedOTP(VALID_USERNAME);
    log.info("Generated Code: {}", verificationTokenCodeOther.getCode());
    assertThat(verificationTokenCode).isEqualTo(verificationTokenCodeOther);
  }

  @Test
  void when_changeUserPassword_withValidDAta_isTrue() {
    User user = userRepository.findByUsernameIgnoreCase(VALID_USERNAME).orElse(null);
    assertThat(user).isNotNull();
    boolean isChanged = passwordService.changeUserPassword(user.getId(), VALID_PASSWORD, "1234");
    assertThat(isChanged).isTrue();
  }

  @Test
  void when_resetPassword_withValidData_isTrue() {
    VerificationTokenCode verificationTokenCode =
        passwordService.generateShortLivedOTP(VALID_USERNAME);

    boolean isReset =
        passwordService.resetPassword(VALID_USERNAME, verificationTokenCode.getCode(), "1234");
    assertThat(isReset).isTrue();
  }

  @Test
  void when_expireVerificationCode_withValidId_shouldExpire() {
    User user = userRepository.findByUsernameIgnoreCase(VALID_USERNAME).orElse(null);
    assertThat(user).isNotNull();
    VerificationTokenCode verificationTokenCode =
        passwordService.generateShortLivedOTP(VALID_USERNAME);

    passwordService.expireVerificationCode(user.getId());

    boolean isValid =
        passwordService.isShortLivedOtpValid(user.getUsername(), verificationTokenCode.getCode());
    assertThat(isValid).isFalse();
  }
}
