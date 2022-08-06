package bg.autohouse.core.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileScannerBean {
  private final Environment environment;

  ProfileScannerBean(Environment environment) {
    this.environment = environment;
  }

  @PostConstruct
  void postConstruct() {
    String[] activeProfiles = environment.getActiveProfiles();
    System.out.printf("active profiles: %s\n", Arrays.toString(activeProfiles));
  }
}
