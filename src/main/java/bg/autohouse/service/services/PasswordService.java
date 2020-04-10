package bg.autohouse.service.services;

import bg.autohouse.security.jwt.JwtToken;

public interface PasswordService {
  JwtToken generateRegistrationToken(String username);

  JwtToken generatePasswordResetToken(String username);

  boolean verifyEmailToken(String token);

  boolean resetPassword(String token, String password);

  void invalidateRegistrationToken(String username);

  void invalidateResetToken(String username);
}
