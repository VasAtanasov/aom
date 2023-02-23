package com.github.vaatech.aom.config;

import com.github.vaatech.aom.core.repository.ApplicationJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.github.vaatech.aom.core.repository",
    repositoryBaseClass = ApplicationJpaRepositoryImpl.class)
public class JPAConfiguration {}
