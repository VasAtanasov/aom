package com.github.vaatech.aom.config;

import com.github.vaatech.aom.feature.common.repository.ApplicationJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "bg.autohouse.backend.feature",
    repositoryBaseClass = ApplicationJpaRepositoryImpl.class)
public class JPAConfiguration {}
