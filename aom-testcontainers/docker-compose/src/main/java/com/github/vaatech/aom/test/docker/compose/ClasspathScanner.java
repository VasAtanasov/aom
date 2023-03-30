package com.github.vaatech.aom.test.docker.compose;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Collections;
import java.util.stream.Stream;

@Slf4j
class ClasspathScanner {

  static Stream<URL> scanFor(final String name) {
    return getAllPropertyFilesOnClassloader(name, ClasspathScanner.class.getClassLoader());
  }

  private static Stream<URL> getAllPropertyFilesOnClassloader(
      final String s, final ClassLoader it) {
    try {
      return Collections.list(it.getResources(s)).stream();
    } catch (Exception e) {
      log.error("Unable to read configuration from classloader {} - this is probably a bug", it, e);
      return Stream.empty();
    }
  }
}
