package bg.autohouse.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "bg.autohouse")
public class Application
{

    private final RestTemplate restTemplate;

    public Application(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}