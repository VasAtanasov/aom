package bg.autohouse.service.services;

import bg.autohouse.data.models.VerificationTokenCode;

public interface PasswordService {
  boolean validateCredentials(String username, String password);

  boolean isExpired(VerificationTokenCode tokenCode);

  VerificationTokenCode generateShortLivedOTP(String username);

  boolean isShortLivedOtpValid(String username, String code);

  boolean resetPassword(String username, String code, String password);

  boolean changeUserPassword(String userId, String oldPassword, String newPassword);

  void expireVerificationCode(String userId);

  String generateRandomPassword();
}
