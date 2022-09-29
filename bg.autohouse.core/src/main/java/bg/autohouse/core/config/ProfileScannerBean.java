package bg.autohouse.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

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
