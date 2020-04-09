package bg.autohouse.service.services;

import bg.autohouse.security.jwt.JwtToken;

public interface PasswordService {
  JwtToken generateRegistrationToken(String username);
}
