package com.github.vaatech.aom.test.docker.common;

public class DockerNotPresentException extends IllegalStateException {
    public DockerNotPresentException(String s) {
        super(s);
    }
}