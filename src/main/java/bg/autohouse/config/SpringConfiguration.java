package bg.autohouse.config;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class SpringConfiguration {

  @Value("${app.time-zone}")
  private String timeZone = "UTC";

  /** Set system {@link TimeZone} to match setting used for database connection */
  @PostConstruct
  void systemConfig() {
    TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
  }
}
