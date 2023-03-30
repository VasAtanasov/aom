package com.github.vaatech.aom.test.docker;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class EchoContainer extends GenericContainer<EchoContainer> {
  private EchoContainer(DockerImageName dockerImageName) {
    super(dockerImageName);
  }

  public static EchoContainer tiny() {
    return new EchoContainer(DockerImageName.parse("alpine"));
  }
}
