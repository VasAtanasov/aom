package com.github.vaatech.aom.test.docker;

import com.github.vaatech.aom.DependsOnPostProcessor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

@AutoConfiguration
@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "containers.enabled", matchIfMissing = true)
public class DockerPresenceAutoConfiguration {

  public static final String DOCKER_IS_AVAILABLE = "dockerPresenceMarker";

  @Bean(name = DOCKER_IS_AVAILABLE)
  public DockerPresenceMarker dockerPresenceMarker() {
    return new DockerPresenceMarker(DockerClientFactory.instance().isDockerAvailable());
  }

  @Bean
  public static BeanFactoryPostProcessor dockerContainerDependsOnDockerPostProcessor() {
    return new DependsOnDockerPostProcessor(DockerContainer.class);
  }

  @Bean
  public static BeanFactoryPostProcessor containerDependsOnDockerPostProcessor() {
    return new DependsOnDockerPostProcessor(GenericContainer.class);
  }

  @Bean
  public static BeanFactoryPostProcessor networkDependsOnDockerPostProcessor() {
    return new DependsOnDockerPostProcessor(Network.class);
  }

  static class DependsOnDockerPostProcessor extends DependsOnPostProcessor {
    public DependsOnDockerPostProcessor(Class<?> beanClass) {
      super(beanClass, DOCKER_IS_AVAILABLE);
    }
  }
}
