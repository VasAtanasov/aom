package com.github.vaatech.aom.core;

import com.github.vaatech.aom.config.ApplicationProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

import static org.springframework.util.StringUtils.hasText;

@Component
public class RuntimeEnvironmentUtil {
  private static final long JVM_STARTUP_TIMESTAMP = System.currentTimeMillis();

  @Value("${environment.id}")
  private String environmentId;

  @Value("${git.commit.id.abbrev:}")
  public String gitCommitIdAbbrev;

  @Value("${file.storage.folder}")
  private String fileStorageFolder;

  public String getRevision() {
    if (isDevelopmentEnvironment()) {
      return "" + System.currentTimeMillis();
    }

    return hasText(gitCommitIdAbbrev) ? gitCommitIdAbbrev : Long.toHexString(JVM_STARTUP_TIMESTAMP);
  }

  public String getEnvironmentId() {
//    return ApplicationProfile.activeProfileId(activeProfiles).orElseThrow();
    return environmentId;
  }

  public boolean isDevelopmentEnvironment() {
    return ApplicationProfile.DEV.getProfile().equalsIgnoreCase(getEnvironmentId());
  }

  public boolean isProductionEnvironment() {
    return ApplicationProfile.PRODUCTION.getProfile().equalsIgnoreCase(getEnvironmentId());
  }

  public boolean isIntegrationTestEnvironment() {
    return ApplicationProfile.TEST.getProfile().equals(getEnvironmentId());
  }

  public String getFileStorageBasePath() {
    return fileStorageFolder;
  }
}
