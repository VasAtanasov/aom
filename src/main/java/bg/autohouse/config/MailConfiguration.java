package bg.autohouse.config;

import bg.autohouse.config.properties.MailProperties;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Import(MailProperties.class)
public class MailConfiguration {

  @Autowired private MailProperties mailProperties;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setProtocol("smtp");
    mailSender.setUsername(mailProperties.getDefaultFromAddress());
    mailSender.setPassword(mailProperties.getPassword());
    Properties props = mailProperties.getJavaMailProperties();
    mailSender.setJavaMailProperties(props);
    return mailSender;
  }
}
