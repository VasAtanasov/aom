package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.UserLog;
import bg.autohouse.data.models.VerificationTokenCode;
import bg.autohouse.data.models.enums.UserLogType;
import bg.autohouse.data.repositories.UserLogRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.data.repositories.VerificationTokenCodeRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.StringGenericUtils;
import bg.autohouse.util.TimeUtils;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PasswordServiceImpl implements PasswordService {
  private static final int TOKEN_LIFE_SPAN_MINUTES = 5;
  // TODO change Objects.requireNonNull with Assert.NotNull
  private final VerificationTokenCodeRepository verificationTokenCodeRepository;
  private final UserLogRepository userLogRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Override
  @Transactional(readOnly = true)
  public boolean validateCredentials(String username, String password) {
    User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
    if (user == null || !encoder.matches(password, user.getPassword())) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isExpired(VerificationTokenCode tokenCode) {
    return TimeUtils.now().after(tokenCode.getExpiryDateTime());
  }

  @Override
  @Transactional
  public VerificationTokenCode generateShortLivedOTP(String username) {
    Assert.notNull(username, ExceptionsMessages.USER_NOT_FOUND_USERNAME);
    VerificationTokenCode token = verificationTokenCodeRepository.findByUsername(username);
    final String code = String.valueOf(100000 + new SecureRandom().nextInt(999999));
    if (token == null) {
      token = new VerificationTokenCode(username, code, null);
      Date now = TimeUtils.now();
      long defaultExpiration = Duration.ofMinutes(TOKEN_LIFE_SPAN_MINUTES).toMillis();
      token.setExpiryDateTime(TimeUtils.dateOf(now.getTime() + defaultExpiration));
      return verificationTokenCodeRepository.save(token);
    }
    if (isExpired(token)) {
      // an OTP exists but it is stale
      log.info(
          "found an OTP, but it's stale, time now = {}, expiry time = {}",
          TimeUtils.now(),
          token.getExpiryDateTime().toString());
      VerificationTokenCode newToken = new VerificationTokenCode(username, code, null);
      Date now = TimeUtils.now();
      long defaultExpiration = Duration.ofMinutes(TOKEN_LIFE_SPAN_MINUTES).toMillis();
      newToken.setExpiryDateTime(TimeUtils.dateOf(now.getTime() + defaultExpiration));
      verificationTokenCodeRepository.delete(token);
      verificationTokenCodeRepository.save(newToken);
      return newToken;
    }
    return token;
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isShortLivedOtpValid(String username, String code) {
    if (username == null || code == null) return false;
    log.info("checking for token by username: {}", username);
    VerificationTokenCode token =
        verificationTokenCodeRepository.findByUsernameAndCode(username, code);
    if (token == null) return false;
    log.info("checking token expiry ...");
    if (isExpired(token)) return false;
    log.info("checking codes: {}, {}", code, token.getCode());
    boolean valid = code.equals(token.getCode());
    if (!valid) token.incrementTokenAttempts();
    log.info("returning {}", valid);
    return valid;
  }

  @Override
  @Transactional
  public boolean changeUserPassword(UUID userId, String oldPassword, String newPassword) {
    Objects.requireNonNull(userId);
    Objects.requireNonNull(oldPassword);
    Objects.requireNonNull(newPassword);
    User user = userRepository.findByIdWithRoles(userId).orElse(null);
    if (!Assert.has(user)) return false;
    if (!encoder.matches(oldPassword, user.getPassword())) return false;
    String encodedPassword = encoder.encode(newPassword);
    user.setPassword(encodedPassword);
    userRepository.save(user);
    userLogRepository.save(new UserLog(user.getId(), UserLogType.USER_CHANGED_PASSWORD, null));
    return true;
  }

  @Override
  @Transactional
  public boolean resetPassword(String username, String code, String password) {
    if (!Assert.has(code) || !Assert.has(password) || !Assert.has(username)) {
      return false;
    }
    User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
    if (user == null) return false;
    user.setPassword(encoder.encode(password));
    userRepository.save(user);
    expireVerificationCode(user.getId());
    return true;
  }

  @Override
  @Transactional
  public void expireVerificationCode(UUID userId) {
    Objects.requireNonNull(userId);
    User user = userRepository.findByIdWithRoles(userId).orElse(null);
    if (user == null) return;
    VerificationTokenCode token =
        verificationTokenCodeRepository.findByUsername(user.getUsername());
    if (token != null) {
      token.setExpiryDateTime(TimeUtils.now());
      verificationTokenCodeRepository.save(token);
    }
  }

  @Override
  public String generateRandomPassword() {
    return StringGenericUtils.nextPassword(12);
  }
}
