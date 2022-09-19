package bg.autohouse.core.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileScannerBean {
  private final Environment environment;

  ProfileScannerBean(Environment environment) {
    this.environment = environment;
  }

  @PostConstruct
  void postConstruct() {
    String[] activeProfiles = environment.getActiveProfiles();
    log.info("Active profiles: {}", Arrays.toString(activeProfiles));
  }
}
