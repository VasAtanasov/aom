package com.github.vaatech.aom.test.docker;

public record DockerPresenceMarker(boolean dockerPresent) {
  public DockerPresenceMarker {
    if (!dockerPresent) {
      throw new DockerNotPresentException(
          "Docker must be present in order for testcontainers to work properly!");
    }
  }
}
