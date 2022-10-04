package bg.autohouse.backend.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController
{
  @GetMapping
  public String hello() {
    return "Hello from Auto House";
  }
}
