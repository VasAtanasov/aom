package bg.autohouse.backend;

import bg.autohouse.backend.config.JPAConfiguration;
import bg.autohouse.backend.config.RestControllerBeanConfiguration;
import bg.autohouse.util.common.logging.ApplicationLoggerFactory;
import bg.autohouse.util.common.logging.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "bg.autohouse.*"))
@Import({JPAConfiguration.class, RestControllerBeanConfiguration.class})
public class Application implements CommandLineRunner {

  private static final Logger log = ApplicationLoggerFactory.getLogger(Application.class);

  private final ConfigurableEnvironment env;

  public Application(ConfigurableEnvironment env) {
    this.env = env;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.debug("{}", env.getProperty("spring.jpa.hibernate.ddl-auto"));
    log.debug("{}", env.getProperty("spring.datasource.url"));
    String[] activeProfiles = env.getActiveProfiles();
    log.debug("Active profiles: {}", Arrays.toString(activeProfiles));
  }
}
