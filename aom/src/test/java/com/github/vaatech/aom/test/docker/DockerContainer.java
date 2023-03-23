package com.github.vaatech.aom.test.docker;

import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startable;

public interface DockerContainer extends Startable, ContainerState {
    GenericContainer<?> unwrap();

    String name();
}
