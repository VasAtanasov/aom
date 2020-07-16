package bg.autohouse.config.properties;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:configuration/mail.properties")
public class MailProperties {

  public static final int MAX_RECIPIENT_SEND_FAILURES = 2;
  public static final int BATCH_SIZE = 100;

  @Value("${mail.enabled}")
  boolean mailDeliveryEnabled;

  @Value("${mail.address.from}")
  private String defaultFromAddress;

  @Value("${mail.address.password}")
  private String password;

  public boolean isMailDeliveryEnabled() {
    return mailDeliveryEnabled;
  }

  public String getDefaultFromAddress() {
    return defaultFromAddress;
  }

  // How many times to retry after mail delivery error?
  public int getMaxFailuresForRecipient() {
    return MAX_RECIPIENT_SEND_FAILURES;
  }

  // How many mail messages are sent in one batch operation?
  public int getBatchSize() {
    return BATCH_SIZE;
  }

  public Properties getJavaMailProperties() {
    final Properties properties = new Properties();
    properties.setProperty("mail.smtp.connectiontimeout", "100000");
    properties.setProperty("mail.smtp.timeout", "50000");
    properties.setProperty("mail.transport.protocol", "smtp");
    properties.setProperty("mail.smtp.auth", "true");
    properties.setProperty("mail.smtp.starttls.enable", "true");
    properties.setProperty("mail.debug", "true");
    return properties;
  }

  public String getPassword() {
    return password;
  }
}
