package bg.autohouse.service.services.impl;

import static bg.autohouse.security.jwt.JwtTokenSpecifications.*;

import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.security.jwt.JwtToken;
import bg.autohouse.security.jwt.JwtTokenCreateRequest;
import bg.autohouse.security.jwt.JwtTokenProvider;
import bg.autohouse.security.jwt.JwtTokenRepository;
import bg.autohouse.security.jwt.JwtTokenSpecifications;
import bg.autohouse.security.jwt.JwtTokenType;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PasswordServiceImpl implements PasswordService {
  private final JwtTokenProvider tokenProvider;
  private final JwtTokenRepository tokenRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Override
  public JwtToken generateRegistrationToken(String username) {
    Assert.notNull(username, "User name is required for token creation");

    JwtToken token =
        tokenRepository
            .findOne(forUser(username).and(withType(JwtTokenType.REGISTRATION)))
            .orElse(null);

    if (token == null) {
      JwtTokenCreateRequest request =
          new JwtTokenCreateRequest(JwtTokenType.REGISTRATION, username);
      token = tokenProvider.createJwtEntity(request);
      return tokenRepository.save(token);
    }

    if (tokenProvider.hasTokenExpired(token.getValue())) {
      log.info(
          "found at token, but it's stale, time now = {}, expiry time = {}",
          new Date(),
          token.getExpirationTime().toString());

      JwtTokenCreateRequest request =
          new JwtTokenCreateRequest(JwtTokenType.REGISTRATION, username);
      token = tokenProvider.createJwtEntity(request);
      tokenRepository.delete(token);
      tokenRepository.save(token);
    }

    return token;
  }

  @Override
  public boolean verifyEmailToken(String token) {

    if (!Assert.has(token)) return false;

    log.info("checking token expiry ...");
    if (tokenProvider.hasTokenExpired(token)) return false;

    if (!tokenProvider.validateToken(token)) return false;

    String username = tokenProvider.getUsernameFromJWT(token);
    String tokenTypeString = tokenProvider.getTokenTypeFromJWT(token);

    if (!Assert.has(username) || !Assert.has(tokenTypeString)) return false;

    JwtTokenType tokenType = EnumUtils.fromString(tokenTypeString, JwtTokenType.class).orElse(null);

    if (!Assert.has(tokenType)) return false;

    JwtToken tokenEntity =
        tokenRepository
            .findOne(forUser(username).and(withType(tokenType).and(withValue(token))))
            .orElse(null);

    if (!Assert.has(tokenEntity)) return false;

    log.info("checking codes: {}, {}", token, tokenEntity.getValue());
    boolean valid = token.equals(tokenEntity.getValue());
    log.info("returning {}", valid);
    return valid;
  }

  @Override
  public void invalidateRegistrationToken(String username) {
    JwtToken tokenEntity =
        tokenRepository
            .findOne(forUser(username).and(withType(JwtTokenType.REGISTRATION)))
            .orElse(null);

    if (tokenEntity != null) {
      tokenRepository.delete(tokenEntity);
    }
  }

  @Override
  public boolean requestPasswordReset(String username) {

    if (!Assert.has(username)) {
      return false;
    }

    User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);

    if (user == null) return false;

    if (!user.isEnabled()) {
      log.error("User registration not verified.");
      return false;
    }

    JwtTokenCreateRequest request = new JwtTokenCreateRequest(JwtTokenType.RESET, user);
    JwtToken passwordResetToken = tokenProvider.createJwtEntity(request);

    log.info("Password reset token: " + passwordResetToken.getValue());
    tokenRepository.save(passwordResetToken);

    // TODO uncomment to when email enabled
    // emailService.sendPasswordResetRequest(
    //     user.getFirstName(), user.getUsername()), passwordResetToken.getValue());

    return true;
  }

  @Override
  public boolean resetPassword(String token, String password) {

    if (!Assert.has(token) && !Assert.has(password)) {
      return false;
    }

    if (tokenProvider.hasTokenExpired(token)) {
      return false;
    }

    if (!tokenProvider.validateToken(token)) {
      return false;
    }

    String userId = tokenProvider.getUserIdFromJWT(token);

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(ExceptionsMessages.EXCEPTION_USER_NOT_FOUND_ID));

    JwtToken tokenEntity =
        tokenRepository
            .findOne(
                JwtTokenSpecifications.forUser(user.getUsername())
                    .and(JwtTokenSpecifications.withValue(token)))
            .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.INVALID_TOKEN));

    if (!EnumUtils.has(tokenEntity.getType(), JwtTokenType.class)
        && tokenEntity.getType().equals(JwtTokenType.RESET)) {
      return false;
    }

    user.setPassword(encoder.encode(password));
    userRepository.save(user);
    tokenRepository.delete(tokenEntity);

    return true;
  }
}
