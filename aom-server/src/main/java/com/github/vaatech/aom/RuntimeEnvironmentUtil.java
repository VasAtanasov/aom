package com.github.vaatech.aom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

@Component
public class RuntimeEnvironmentUtil {
    private static final long JVM_STARTUP_TIMESTAMP = System.currentTimeMillis();

    @Value("${app.dev.mode:}")
    private boolean isDevMode;

    @Value("${git.commit.id.abbrev:}")
    public String gitCommitIdAbbrev;

    @Value("${app.file.storage.folder}")
    private String fileStorageFolder;

    public String getRevision() {
        return hasText(gitCommitIdAbbrev) ? gitCommitIdAbbrev : Long.toHexString(JVM_STARTUP_TIMESTAMP);
    }

    public String getFileStorageBasePath() {
        return fileStorageFolder;
    }

    public boolean isDevMode() {
        return isDevMode;
    }
}
