package bg.autohouse.service.services;

public interface EmailService {
  void verifyEmail(String email, String token);

  void sendPasswordResetRequest(String firstName, String email, String token);
}
