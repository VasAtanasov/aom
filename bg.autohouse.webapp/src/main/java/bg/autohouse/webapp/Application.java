package bg.autohouse.webapp;

import bg.autohouse.core.AutohouseApplication;
import bg.autohouse.util.common.logging.ApplicationLoggerFactory;
import bg.autohouse.util.common.logging.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static bg.autohouse.webapp.Application.BASE_PACKAGE;

@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
@EntityScan(basePackages = BASE_PACKAGE)
@EnableJpaRepositories(basePackages = BASE_PACKAGE)
public class Application implements CommandLineRunner {

  private static final Logger log = ApplicationLoggerFactory.getLogger(Application.class);

  public static final String BASE_PACKAGE = "bg.autohouse";
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
    int a = 5;
  }
}
