package bg.autohouse.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;

@Slf4j
@EnableTransactionManagement
public class AutohouseApplication extends SpringApplication {

  public AutohouseApplication(Class<?> configClass) {
    super(configClass);
  }

  /** Enforce activation of profiles defined in {@link ApplicationProfiles}. */
  @Override
  protected void configureProfiles(ConfigurableEnvironment environment, String[] args) {
    super.configureProfiles(environment, args);

    int numberActive = 0;

    if (environment.acceptsProfiles(Profiles.of(ApplicationProfiles.INMEMORY))) numberActive++;
    if (environment.acceptsProfiles(Profiles.of(ApplicationProfiles.DEV))) numberActive++;
    if (environment.acceptsProfiles(Profiles.of(ApplicationProfiles.STAGING))) numberActive++;
    if (environment.acceptsProfiles(Profiles.of(ApplicationProfiles.PRODUCTION))) numberActive++;

    if (numberActive > 1) {
      throw new IllegalStateException(
          format(
              "Only one of the following profiles may be specified: [%s]",
              arrayToCommaDelimitedString(
                  new String[] {
                    ApplicationProfiles.PRODUCTION,
                    ApplicationProfiles.INMEMORY,
                    ApplicationProfiles.DEV
                  })));
    }

    if (numberActive == 1) {
      log.info("Activating because one profile has been specified.");
    } else {
      log.info(
          "The default 'standalone' profile is active because no other profiles have been specified.");
      environment.addActiveProfile(ApplicationProfiles.INMEMORY);
    }
  }
}
