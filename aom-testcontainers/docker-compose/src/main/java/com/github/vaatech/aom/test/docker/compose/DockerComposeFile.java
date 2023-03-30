package com.github.vaatech.aom.test.docker.compose;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

@Slf4j
enum DockerComposeFile {
  INSTANCE;

  private static final String DOCKER_COMPOSE_FILE = "docker-compose.yml";

  private Map<String, Object> data;
  private File file;

  DockerComposeFile() {
    Yaml yaml = new Yaml();
    var url =
        ClasspathScanner.scanFor(DOCKER_COMPOSE_FILE)
            .findFirst()
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        "Could not find " + DOCKER_COMPOSE_FILE + " on classpath"));

    this.file = new File(url.getFile());

    try (InputStream inputStream = url.openStream()) {
      this.data = yaml.load(inputStream);
    } catch (FileNotFoundException e) {
      logWarning(
          "Attempted to read docker compose file at {} but the file was not found. Exception message: {}",
          url,
          e);
    } catch (IOException e) {
      logWarning(
          "Attempted to read docker compose file at {} but could it not be loaded. Exception message: {}",
          url,
          e);
    }

    if (data == null) {
      throw new IllegalStateException("Could not load " + DOCKER_COMPOSE_FILE + " from " + url);
    }
  }

  private void logWarning(String message, URL url, Exception e) {
    log.warn(message, url, e);
  }

  public Map<String, Object> getData() {
    return data;
  }

  public File getFile() {
    return file;
  }
}
