package com.github.vaatech.aom.test.docker;

import com.github.vaatech.aom.test.docker.common.DockerNotPresentException;
import com.github.vaatech.aom.test.docker.common.DockerPresenceMarker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DockerPresenceMarkerTest {

  @Test
  void markerShouldBlockContextIfDockerIsAbsent() {
    assertThatThrownBy(() -> new DockerPresenceMarker(false))
        .isInstanceOf(DockerNotPresentException.class);
  }
}
