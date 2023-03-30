package com.github.vaatech.aom.test.docker.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.util.Arrays;
import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@Slf4j
@Configuration(proxyBeanMethods = false)
@AutoConfigureOrder(value = Ordered.LOWEST_PRECEDENCE)
@ConditionalOnProperty(name = "containers.enabled", matchIfMissing = true)
public class DockerContainersConfiguration {

  private static final Logger LOGGER =
      LogManager.getFormatterLogger(DockerContainersConfiguration.class);

  public static final String DOCKER_ENVIRONMENT = "dockerEnvironment";

  @Bean
  @ConditionalOnMissingBean(Network.class)
  public Network network() {
    Network network = Network.newNetwork();
    log.info("Created docker Network with id={}", network.getId());
    return network;
  }

  @Bean(name = DOCKER_ENVIRONMENT)
  public DockerEnvironment dockerContainers(
      @Autowired(required = false) DockerContainer[] containers) throws Exception {

    if (containers == null) {
      return new DockerEnvironment(false, new GenericContainer<?>[0]);
    }

    ContainerUtils.run(containers);
    Callable<Boolean> allHealthy =
        () -> Arrays.stream(containers).allMatch(ContainerState::isHealthy);
    await().atMost(30, SECONDS).pollInterval(500, MICROSECONDS).until(allHealthy);

    Arrays.stream(containers)
        .forEach(c -> ContainerUtils.logContainerInfo(c.name(), c.unwrap(), LOGGER));

    return new DockerEnvironment(
        allHealthy.call(),
        Arrays.stream(containers).map(DockerContainer::unwrap).toArray(GenericContainer<?>[]::new));
  }
}
