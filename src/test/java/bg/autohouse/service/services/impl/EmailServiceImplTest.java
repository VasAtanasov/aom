package bg.autohouse.service.services.impl;

import bg.autohouse.config.properties.MailProperties;
import bg.autohouse.service.services.EmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.TemplateEngine;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailServiceImplTest {

  @Autowired JavaMailSenderImpl emailSender;
  @Autowired MailProperties mailProperties;

  @Autowired
  @Qualifier("emailTemplateEngine")
  TemplateEngine templateEngine;

  GreenMail testSmtp;
  EmailService emailService;

  @BeforeEach
  public void testSmtpInit() {
    testSmtp = new GreenMail(ServerSetupTest.SMTP_IMAP);
    testSmtp.setUser(mailProperties.getDefaultFromAddress(), mailProperties.getPassword());
    testSmtp.start();
    emailSender.setPort(3025);
    emailSender.setHost("localhost");
    emailService = new EmailServiceImpl(emailSender, templateEngine);
  }

  @Test
  @DisplayName("Send test")
  void testSend() {
    GreenMailUtil.sendTextEmailTest("to@localhost", "from@localhost", "some subject", "some body");
    final MimeMessage[] receivedMessages = testSmtp.getReceivedMessages();
    final MimeMessage receivedMessage = receivedMessages[0];
    assertThat("some body").isEqualTo(GreenMailUtil.getBody(receivedMessage));
  }

  @Test
  public void when_verifyEmail_thenSend() {
    final String token = "1041794";
    emailService.verifyEmail("to@localhost", token);
    Message[] messages = testSmtp.getReceivedMessages();
    assertThat(messages.length).isEqualTo(1);
    String body = GreenMailUtil.getBody(messages[0]);
    assertThat(body).contains(token);
  }

  @Test
  public void when_sendPasswordResetRequest_thenSend() {
    final String token = "123124";
    final String firstName = "firstName";
    emailService.sendPasswordResetRequest(firstName, "to@localhost", token);
    Message[] messages = testSmtp.getReceivedMessages();
    assertThat(messages.length).isEqualTo(1);
    String body = GreenMailUtil.getBody(messages[0]);
    assertThat(body).contains(token);
    assertThat(body).contains(firstName);
  }

  @AfterEach
  public void cleanup() {
    testSmtp.stop();
  }
}
