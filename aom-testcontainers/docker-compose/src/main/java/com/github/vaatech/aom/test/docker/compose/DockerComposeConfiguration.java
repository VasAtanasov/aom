package com.github.vaatech.aom.test.docker.compose;

import com.github.vaatech.aom.test.docker.common.DockerPresenceAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(DockerPresenceAutoConfiguration.class)
@ConditionalOnProperty(name = "containers.enabled", matchIfMissing = true)
public class DockerComposeConfiguration {

  private static final String DOCKER_COMPOSE_CONTAINERS = "dockerComposeContainers";

  @Bean(name = DOCKER_COMPOSE_CONTAINERS)
  public ContainerState[] dockerComposeContext() {

    var data = DockerComposeFile.INSTANCE.getData();
    var file = DockerComposeFile.INSTANCE.getFile();

    DockerComposeContainer<?> environment =
        new DockerComposeContainer<>(file).withExposedService("mysql", 3306)
                .withLogConsumer("mysql", (frame) -> System.out.println(frame.getUtf8String()));
    environment.start();


    var state = environment.getContainerByServiceName("mysql").get();

    int a = 5;

    return new ContainerState[] {state};
  }
}
