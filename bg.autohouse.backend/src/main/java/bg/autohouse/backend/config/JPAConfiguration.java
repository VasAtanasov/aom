package bg.autohouse.backend.config;

import bg.autohouse.backend.feature.common.repository.ApplicationJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "bg.autohouse.backend.feature",
    repositoryBaseClass = ApplicationJpaRepositoryImpl.class)
public class JPAConfiguration {}
