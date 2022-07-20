package bg.autohouse.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "bg.autohouse")
public class SpiderApplication
{
    private final RestTemplate restTemplate;

    public SpiderApplication(RestTemplate restTemplate)
    {
        System.out.println(restTemplate != null);
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(SpiderApplication.class, args);
    }
}
