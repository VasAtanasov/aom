package com.github.vaatech.aom.test.docker;

import com.github.dockerjava.api.command.InspectContainerResponse;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

public class StartableDecorator<SELF extends GenericContainer<SELF>> implements DockerContainer {

    private final String name;
    private final GenericContainer<SELF> genericContainer;
    private final Runnable afterStartCallback;

    public StartableDecorator(String name, GenericContainer<SELF> genericContainer, Runnable afterStartCallback) {
        this.name = name;
        this.genericContainer = genericContainer;
        this.afterStartCallback = afterStartCallback;
    }

    @Override
    public void start() {
        genericContainer.start();
        afterStartCallback.run();
    }

    @Override
    public void stop() {
        genericContainer.stop();
    }

    @Override
    public List<Integer> getExposedPorts() {
        return genericContainer.getExposedPorts();
    }

    @Override
    public InspectContainerResponse getContainerInfo() {
        return genericContainer.getContainerInfo();
    }

    @Override
    public GenericContainer<SELF> unwrap() {
        return genericContainer;
    }

    @Override
    public String name() {
        return this.name;
    }
}
