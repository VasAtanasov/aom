package com.github.vaatech.aom.test.docker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CommonContainerProperties {
  private DockerImage dockerImage;

  public abstract DockerImage getDefaultDockerImage();

  public record DockerImage(String registry, String name, String version) {

    public DockerImage {
      if (registry != null && registry.isBlank()) {
        throw new IllegalArgumentException("Registry cannot be blank.");
      }
      if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Name cannot be blank.");
      }
      if (version != null && version.isBlank()) {
        throw new IllegalArgumentException("Version cannot be blank.");
      }
    }

    public String fullImageName() {
      return String.format("%s/%s:%s", registry, name, version);
    }
  }
}
