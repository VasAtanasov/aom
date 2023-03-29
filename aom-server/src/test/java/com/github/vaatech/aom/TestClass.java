package com.github.vaatech.aom;

import com.github.vaatech.aom.test.docker.DockerEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@EnabledIf(expression = "#{dockerEnvironment != null && dockerEnvironment.dockerEnvironmentUp}", loadContext = true)
public class TestClass extends BaseApplicationTest {

  @Autowired DockerEnvironment dockerEnvironment;

  @Test
  void shouldLoad() {

    assertThat(dockerEnvironment).isNotNull();
  }
}
