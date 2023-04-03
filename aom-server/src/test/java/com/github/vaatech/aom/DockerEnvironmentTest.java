package com.github.vaatech.aom;

import com.github.vaatech.test.docker.common.spring.DockerEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import static org.assertj.core.api.Assertions.assertThat;

@EnabledIf(expression = "#{environment.getProperty('containers.enabled')}", loadContext = true)
class DockerEnvironmentTest extends BaseApplicationTest {

  @Autowired
  DockerEnvironment dockerEnvironment;

  @Test
  void shouldLoadDockerEnvironment() {
    assertThat(dockerEnvironment).isNotNull();
    assertThat(dockerEnvironment.dockerEnvironmentUp()).isTrue();
  }
}
