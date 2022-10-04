package bg.autohouse.backend;

import bg.autohouse.backend.config.AutohouseApplication;
import bg.autohouse.util.common.logging.ApplicationLoggerFactory;
import bg.autohouse.util.common.logging.Logger;
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
    AutohouseApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("{}", env.getProperty("spring.jpa.hibernate.ddl-auto"));
    log.info("{}", env.getProperty("spring.datasource.url"));
    String[] activeProfiles = env.getActiveProfiles();
    log.info("Active profiles: {}", Arrays.toString(activeProfiles));
  }
}
