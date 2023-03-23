package com.github.vaatech.aom.test.docker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CommonContainerProperties {
  private String dockerImage;
  private String dockerImageVersion;

  public abstract String getDefaultDockerImage();
}
