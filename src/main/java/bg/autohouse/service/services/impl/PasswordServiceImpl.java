package bg.autohouse.service.services.impl;

import static bg.autohouse.security.jwt.JwtTokenSpecifications.*;

import bg.autohouse.security.jwt.JwtToken;
import bg.autohouse.security.jwt.JwtTokenCreateRequest;
import bg.autohouse.security.jwt.JwtTokenProvider;
import bg.autohouse.security.jwt.JwtTokenRepository;
import bg.autohouse.security.jwt.JwtTokenType;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.util.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PasswordServiceImpl implements PasswordService {
  private final JwtTokenProvider tokenProvider;
  private final JwtTokenRepository tokenRepository;

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

    return null;
  }
}
