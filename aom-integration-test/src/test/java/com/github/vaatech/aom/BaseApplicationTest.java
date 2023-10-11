package com.github.vaatech.aom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vaatech.aom.test.DatabaseCleaner;
import com.github.vaatech.aom.test.listeners.CleanDatabaseTestExecutionListener;
import com.github.vaatech.aom.test.rest.PrettyPrintPayloadCommonsRequestLoggingFilter;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(
        classes = {Application.class, BaseApplicationTest.ApplicationTestConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners(
        listeners = CleanDatabaseTestExecutionListener.class,
        mergeMode = MERGE_WITH_DEFAULTS)
public abstract class BaseApplicationTest {

    @SpringBootApplication
    public static class ApplicationTestConfiguration {
        @Bean
        @ConditionalOnProperty(prefix = "spring", name = "flyway.enabled")
        public FlywayMigrationStrategy flywayMigrationStrategy() {
            return flyway -> {
                // ignore to avoid migration on startup.
                log.warn("Flyway migration on startup is skipped in test.");
            };
        }

        @Bean
        public CommonsRequestLoggingFilter logFilter(ObjectMapper objectMapper) {
            CommonsRequestLoggingFilter filter =
                    new PrettyPrintPayloadCommonsRequestLoggingFilter(objectMapper);
            filter.setIncludeQueryString(true);
            filter.setIncludePayload(true);
            filter.setIncludeHeaders(true);
            filter.setIncludeClientInfo(true);
            filter.setMaxPayloadLength(20000);
            return filter;
        }
    }

    @LocalServerPort
    protected int port;

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void initTest() {
        reset();
    }

    protected void reset() {
//    databaseCleaner.clearManagedEntityTablesFromDatabase();
    }
}
