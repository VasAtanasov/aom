package com.github.vaatech.aom.test.docker.compose;

import com.github.vaatech.aom.test.docker.common.DockerContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.ContainerState;

@Slf4j
@SpringBootTest(
    classes = {DockerComposeConfigurationTest.TestConfiguration.class},
    properties = {
      "spring.profiles.active=enabled",
    })
public class DockerComposeConfigurationTest {

  @Autowired
  ContainerState[] containers;

  @Test
  void shouldStartContainers() {
    int a = 0;
  }

  @SpringBootApplication(scanBasePackages = "com.github.vaatech.aom.test.docker")
  static class TestConfiguration {
    @Bean
    DockerContainer[] dummyContainers() {
      return new DockerContainer[0];
    }
  }
}
