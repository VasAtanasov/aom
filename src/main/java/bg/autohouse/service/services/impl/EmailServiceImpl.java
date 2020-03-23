package bg.autohouse.service.services.impl;

import bg.autohouse.service.services.EmailService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmailServiceImpl implements EmailService {

  private static final String BASE_URL = "http://localhost:8007/api";

  private final JavaMailSender javaMailSender;

  // The subject line for the email.
  final String SUBJECT = "One last step to complete your registration.";

  final String PASSWORD_RESET_SUBJECT = "Password reset request";

  final String TEXT_BODY =
      "Please verify your email address. "
          + System.lineSeparator()
          + System.lineSeparator()
          + System.lineSeparator()
          + "Thank you for registering with our mobile app. To complete registration process and be able to log in,"
          + " open then the following URL in your browser window: "
          + BASE_URL
          + "/users/email-verification?token=$tokenValue"
          + System.lineSeparator()
          + System.lineSeparator()
          + System.lineSeparator()
          + " Thank you! And we are waiting for you inside!";

  final String PASSWORD_RESET_TEXT_BODY =
      "A request to reset your password "
          + System.lineSeparator()
          + System.lineSeparator()
          + "Hi, $firstName! "
          + System.lineSeparator()
          + System.lineSeparator()
          + "Someone has requested to reset your password with our project. If it were not you, please ignore it."
          + " otherwise please open the link below in your browser window to set a new password:"
          + BASE_URL
          + "/users/email-verification?token=$tokenValue"
          + System.lineSeparator()
          + System.lineSeparator()
          + " Thank you!";

  @Override
  public void verifyEmail(String email, String token) {

    String textBodyWithToken = TEXT_BODY.replace("$tokenValue", token);
    log.info(textBodyWithToken);

    log.info("Sending email with token for registration confirmation...");
    sendSimpleMessage(email, SUBJECT, textBodyWithToken);
    log.info("Email sent!");
  }

  public void sendPasswordResetRequest(String firstName, String email, String token) {

    String textBodyWithToken = PASSWORD_RESET_TEXT_BODY.replace("$tokenValue", token);
    textBodyWithToken = textBodyWithToken.replace("$firstName", firstName);
    log.info(textBodyWithToken);

    log.info("Sending email with token for password reset...");
    sendSimpleMessage(email, SUBJECT, textBodyWithToken);
    log.info("Email sent!");
  }

  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    javaMailSender.send(message);
  }
}
