package com.github.vaatech.aom.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;

import static java.lang.String.format;

@Slf4j
public class AomApplication extends SpringApplication {


  public AomApplication(Class<?> configClass) {
    super(configClass);
  }

  public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
    return new AomApplication(primarySource).run(args);
  }

  /** Enforce activation of profiles defined in {@link ApplicationProfile}. */
  @Override
  protected void postProcessApplicationContext(ConfigurableApplicationContext context) {
    enforceProfiles(context);
    super.postProcessApplicationContext(context);
  }

  private void enforceProfiles(ConfigurableApplicationContext context) {
    ConfigurableEnvironment environment = context.getEnvironment();

    int numberActive = 0;

    for (String profileStr : ApplicationProfile.toProfilesSet()) {
      var profiles = Profiles.of(profileStr);
      if (environment.acceptsProfiles(profiles)) numberActive++;
    }

    if (numberActive > 1) {
      var message =
          format(
              "Only one of the following profiles may be specified: [%s]",
              ApplicationProfile.toAvailableProfilesStr());

      log.error(message);
      throw new IllegalStateException(message);
    }

    if (numberActive == 1) {
      log.info("Activating because one profile has been specified.");
    } else {
      log.info(
          "The default 'standalone' profile is active because no other profiles have been specified.");
      environment.setActiveProfiles(ApplicationProfile.INMEMORY.getProfile());
    }
  }
}
