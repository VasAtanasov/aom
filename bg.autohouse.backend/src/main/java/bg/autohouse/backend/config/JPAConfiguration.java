package bg.autohouse.backend.config;

import bg.autohouse.backend.feature.common.repository.ApplicationRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "bg.autohouse.backend.feature",
    repositoryBaseClass = ApplicationRepositoryImpl.class)
public class JPAConfiguration {}
