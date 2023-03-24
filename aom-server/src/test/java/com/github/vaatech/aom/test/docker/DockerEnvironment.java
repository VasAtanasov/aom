package com.github.vaatech.aom.test.docker;

import org.testcontainers.containers.GenericContainer;

public record DockerEnvironment(boolean dockerEnvironmentUp, GenericContainer<?>[] containers) {
    public DockerEnvironment {
        if (!dockerEnvironmentUp) {
            throw new IllegalStateException("Docker environment not ready!");
        }
    }
}
