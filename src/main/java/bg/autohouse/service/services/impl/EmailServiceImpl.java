package bg.autohouse.service.services.impl;

import bg.autohouse.service.models.EmailServiceModel;
import bg.autohouse.service.models.EmailServiceModel.EmailServiceModelBuilder;
import bg.autohouse.service.services.EmailService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmailServiceImpl implements EmailService {

  private static final String BASE_URL = "http://localhost:8007/api";

  private final JavaMailSender javaMailSender;

  @Qualifier("emailTemplateEngine")
  private final TemplateEngine templateEngine;

  final String REGISTRATION_SUBJECT = "One last step to complete your registration.";

  final String PASSWORD_RESET_SUBJECT = "Password reset request";

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

    EmailServiceModelBuilder mail =
        EmailServiceModel.builder().subject(REGISTRATION_SUBJECT).toAddress(email);

    Context context = new Context();
    context.setVariable("token", token);
    String html = templateEngine.process("html/email_verification", context);
    mail.htmlContent(html);

    try {
      log.info("Sending email with token for registration confirmation...");
      sendEmail(mail.build(), Boolean.TRUE);
    } catch (MessagingException | IOException ex) {
      log.info("Email was not sent", ex);
    }
  }

  public void sendPasswordResetRequest(String firstName, String email, String token) {

    String textBodyWithToken = PASSWORD_RESET_TEXT_BODY.replace("$tokenValue", token);
    textBodyWithToken = textBodyWithToken.replace("$firstName", firstName);
    log.info(textBodyWithToken);

    log.info("Sending email with token for password reset...");
    log.info("Email sent!");
  }

  private void sendEmail(EmailServiceModel mail, boolean isHtml)
      throws MessagingException, IOException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper =
        new MimeMessageHelper(
            message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

    // helper.addAttachment("template-cover.png", new
    // ClassPathResource("javabydeveloper-email.PNG"));

    helper.setTo(mail.getToAddress());
    helper.setText(isHtml ? mail.getHtmlContent() : mail.getTextContent(), isHtml);
    helper.setSubject(mail.getSubject());
    log.info("Email sent!");
    javaMailSender.send(message);
  }
}
