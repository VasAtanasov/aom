package bg.autohouse.webapp;

import bg.autohouse.core.AutohouseApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication(scanBasePackages = "bg.autohouse")
public class Application implements CommandLineRunner {
  private final RestTemplate restTemplate;
  private final ConfigurableEnvironment env;

  public Application(RestTemplate restTemplate, ConfigurableEnvironment env) {
    this.restTemplate = restTemplate;
    this.env = env;
    //        try
    //        {
    //            System.out.println(dataSource.getConnection().getMetaData().getURL());
    //        }
    //        catch (SQLException e)
    //        {
    //            e.printStackTrace();
    //        }

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
