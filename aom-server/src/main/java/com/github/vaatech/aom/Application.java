package com.github.vaatech.aom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

  private final ConfigurableEnvironment env;

  public Application(ConfigurableEnvironment env) {
    this.env = env;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.debug("{}", env.getProperty("spring.jpa.hibernate.ddl-auto"));
    log.debug("{}", env.getProperty("spring.datasource.url"));
    String[] activeProfiles = env.getActiveProfiles();
    log.debug("Active profiles: {}", Arrays.toString(activeProfiles));
  }
}
