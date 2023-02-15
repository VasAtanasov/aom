package bg.autohouse.backend;

import bg.autohouse.backend.config.AutoHouseApplication;
import bg.autohouse.backend.util.common.logging.ApplicationLoggerFactory;
import bg.autohouse.backend.util.common.logging.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

@SpringBootApplication
public class Application implements CommandLineRunner {

  private static final Logger log = ApplicationLoggerFactory.getLogger(Application.class);

  private final ConfigurableEnvironment env;

  public Application(ConfigurableEnvironment env) {
    this.env = env;
  }

  public static void main(String[] args) {
    AutoHouseApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.debug("{}", env.getProperty("spring.jpa.hibernate.ddl-auto"));
    log.debug("{}", env.getProperty("spring.datasource.url"));
    String[] activeProfiles = env.getActiveProfiles();
    log.debug("Active profiles: {}", Arrays.toString(activeProfiles));
  }
}
