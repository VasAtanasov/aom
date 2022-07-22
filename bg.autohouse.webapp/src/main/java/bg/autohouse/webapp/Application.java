package bg.autohouse.webapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@SpringBootApplication(scanBasePackages = "bg.autohouse")
public class Application implements CommandLineRunner
{

    private final RestTemplate restTemplate;
    private final DataSource dataSource;
    private final Environment env;

    public Application(RestTemplate restTemplate, DataSource dataSource, Environment env)
    {
        this.restTemplate = restTemplate;
        this.dataSource = dataSource;
        this.env = env;
        try
        {
            System.out.println(dataSource.getConnection().getMetaData().getURL());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        log.info("{}", env.getProperty("spring.jpa.hibernate.ddl-auto"));
    }
}
