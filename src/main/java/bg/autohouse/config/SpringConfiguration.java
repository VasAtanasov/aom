package bg.autohouse.config;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableConfigurationProperties
public class SpringConfiguration {

  public static final long JVM_STARTUP_TIMESTAMP = System.currentTimeMillis();

  @Value("${app.time-zone}")
  private String timeZone = "UTC";

  /** Set system {@link TimeZone} to match setting used for database connection */
  @PostConstruct
  void systemConfig() {
    TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
  }
}
