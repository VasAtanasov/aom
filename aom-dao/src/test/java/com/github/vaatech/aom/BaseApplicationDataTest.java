package com.github.vaatech.aom;

// import com.github.vaatech.aom.test.listeners.CleanDatabaseTestExecutionListener;

import com.github.vaatech.aom.test.HibernateStatisticsExtension;
import com.github.vaatech.aom.test.HibernateStatisticsHelper;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(
        classes = {/*Application.class,*/ BaseApplicationDataTest.ApplicationTestConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners(
        listeners = {
                /*CleanDatabaseTestExecutionListener.class*/
        },
        mergeMode = MERGE_WITH_DEFAULTS)
public abstract class BaseApplicationDataTest {

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
    }

    @RegisterExtension
    public final HibernateStatisticsExtension statsVerifier =
            new HibernateStatisticsExtension() {
                @Override
                protected Statistics getStatistics() {
                    return getHibernateStatistics();
                }
            };

    @Autowired
    protected EntityManager entityManager;

    protected Statistics getHibernateStatistics() {
        return HibernateStatisticsHelper.getStatistics(entityManager);
    }

    protected void clearHibernateStatistics() {
        getHibernateStatistics().clear();
    }

    @BeforeEach
    public void initTest() {
        reset();
    }

    protected void reset() {
        clearHibernateStatistics();
    }
}
