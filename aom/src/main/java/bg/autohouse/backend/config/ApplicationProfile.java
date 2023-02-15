package bg.autohouse.backend.config;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;

public enum ApplicationProfile {
  INMEMORY("default"),
  DEV("dev"),
  TEST("test"),
  PRODUCTION("prod");

  private final String profile;

  ApplicationProfile(String profile) {
    this.profile = profile;
  }

  public static String toAvailableProfilesStr() {
    return arrayToCommaDelimitedString(toProfilesSet().toArray(new String[0]));
  }

  public static Optional<String> activeProfileId(String[] activeProfiles) {
    Set<String> profiles = toProfilesSet();
    for (String activeProfile : activeProfiles) {
      if (profiles.contains(activeProfile)) return Optional.of(activeProfile);
    }
    return Optional.empty();
  }

  public static Set<String> toProfilesSet() {
    return Arrays.stream(ApplicationProfile.values())
        .map(ApplicationProfile::getProfile)
        .collect(Collectors.toSet());
  }

  public String getProfile() {
    return profile;
  }
}
