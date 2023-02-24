package com.github.vaatech.aom.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.MigrationInfo;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DBFlywayConfig {

  @Bean
  public FlywayMigrationStrategy flywayMigrationStrategy() {
    return flyway -> {
      log.info("=== start db migration === ");
      for (MigrationInfo info : flyway.info().all()) {
        log.info(
            "state: {} desc: {} script: {}",
            info.getState().getDisplayName(),
            info.getDescription(),
            info.getScript());
      }
      flyway.repair();
      flyway.migrate();
      for (MigrationInfo info : flyway.info().all()) {
        log.info(
            "state: {} desc: {} script: {}",
            info.getState().getDisplayName(),
            info.getDescription(),
            info.getScript());
      }
      log.info("=== end db migration === ");
    };
  }
}
