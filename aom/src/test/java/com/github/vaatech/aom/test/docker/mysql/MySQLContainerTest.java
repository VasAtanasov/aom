package com.github.vaatech.aom.test.docker.mysql;

import com.github.vaatech.aom.BaseApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import javax.sql.DataSource;

import static com.github.vaatech.aom.test.docker.mysql.MySQLProperties.BEAN_NAME_CONTAINER_MYSQL;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@EnabledIf(
    expression =
        "#{environment.getProperty('container.mysql.enabled') && environment.getProperty('containers.enabled')}",
    loadContext = true)
public class MySQLContainerTest extends BaseApplicationTest {

  @Autowired ConfigurableListableBeanFactory beanFactory;

  @Autowired ConfigurableEnvironment environment;

  @Autowired JdbcTemplate jdbcTemplate;

  @Test
  public void shouldConnectToMySQL() throws Exception {
    assertThat(jdbcTemplate.queryForObject("select version()", String.class)).startsWith("8.0.");
  }

  @Test
  public void shouldSetupDependsOnForAllDataSources() throws Exception {
    String[] beanNamesForType =
        BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, DataSource.class);
    assertThat(beanNamesForType)
        .as("Auto-configured datasource should be present")
        .hasSize(1)
        .contains("dataSource");
    asList(beanNamesForType).forEach(this::hasDependsOn);
  }

  @Test
  public void propertiesAreAvailable() {
    assertThat(environment.getProperty("container.mysql.port")).isNotEmpty();
    assertThat(environment.getProperty("container.mysql.host")).isNotEmpty();
    assertThat(environment.getProperty("container.mysql.database")).isNotEmpty();
    assertThat(environment.getProperty("container.mysql.username")).isNotEmpty();
    assertThat(environment.getProperty("container.mysql.password")).isNotEmpty();
  }

  private void hasDependsOn(String beanName) {
    assertThat(beanFactory.getBeanDefinition(beanName).getDependsOn())
        .isNotNull()
        .isNotEmpty()
        .contains(BEAN_NAME_CONTAINER_MYSQL);
  }
}
