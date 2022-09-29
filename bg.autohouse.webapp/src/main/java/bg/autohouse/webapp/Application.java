package bg.autohouse.webapp;

import bg.autohouse.core.AutohouseApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static bg.autohouse.webapp.Application.BASE_PACKAGE;

@Slf4j
@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
@EntityScan(basePackages = BASE_PACKAGE)
@EnableJpaRepositories(basePackages = BASE_PACKAGE)
public class Application {

  public static final String BASE_PACKAGE = "bg.autohouse";

  public static void main(String[] args) {
    AutohouseApplication.run(Application.class, args);
  }
}
