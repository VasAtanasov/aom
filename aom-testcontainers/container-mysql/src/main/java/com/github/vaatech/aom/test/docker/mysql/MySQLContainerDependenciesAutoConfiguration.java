package com.github.vaatech.aom.test.docker.mysql;

import com.github.vaatech.aom.DependsOnPostProcessor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import static com.github.vaatech.aom.test.docker.common.DockerContainersConfiguration.DOCKER_ENVIRONMENT;
import static com.github.vaatech.aom.test.docker.mysql.MySQLProperties.BEAN_NAME_CONTAINER_MYSQL;
import static org.springframework.boot.autoconfigure.AutoConfigureOrder.DEFAULT_ORDER;

/**
 * {@code @AutoConfiguration} class for MySQL container dependencies. This configuration class is
 * only activated if the following conditions are met:
 *
 * <ul>
 *   <li>The class {@link DataSource} is present on the classpath
 *   <li>The property "containers.enabled" is set to "true" (or not set at all, as the default value
 *       is "true")
 *   <li>The property "container.mysql.enabled" is set to "true"
 * </ul>
 *
 * <p>Additionally, this configuration class is ordered to be processed after the {@link
 * org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration} class.
 *
 * <p>The configuration class provides a single bean, the {@link BeanFactoryPostProcessor} {@link
 * DependsOnPostProcessor}, which sets the bean named "containerMySql" as a dependency for any
 * {@link DataSource} beans present in the application context.
 */
@AutoConfiguration(
    afterName = "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration")
@AutoConfigureOrder(DEFAULT_ORDER)
@ConditionalOnClass(DataSource.class)
@ConditionalOnExpression("${containers.enabled:true}")
@ConditionalOnProperty(name = "container.mysql.enabled", matchIfMissing = true)
public class MySQLContainerDependenciesAutoConfiguration {

  /**
   * @return {@link BeanFactoryPostProcessor} that sets the {@link DataSource} bean to depend on the
   *     MysSQL container bean.
   */
  @Contract(" -> new")
  @Bean
  public static @NotNull BeanFactoryPostProcessor datasourceMySqlDependencyPostProcessor() {
    return new DependsOnPostProcessor(
        DataSource.class, BEAN_NAME_CONTAINER_MYSQL, DOCKER_ENVIRONMENT);
  }
}
