package bg.autohouse.service.services;

import bg.autohouse.security.jwt.JwtToken;

public interface PasswordService {
  JwtToken generateRegistrationToken(String username);

  boolean verifyEmailToken(String token);

  boolean requestPasswordReset(String username);

  boolean resetPassword(String token, String password);

  void invalidateRegistrationToken(String username);
}
