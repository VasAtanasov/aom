package com.github.vaatech.aom;

import com.github.vaatech.test.common.spring.DockerEnvironment;
import org.assertj.core.api.Assertions;
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
        Assertions.assertThat(dockerEnvironment).isNotNull();
    }
}
