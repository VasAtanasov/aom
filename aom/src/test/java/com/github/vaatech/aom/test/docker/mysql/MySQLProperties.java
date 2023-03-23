package com.github.vaatech.aom.test.docker.mysql;

import com.github.vaatech.aom.test.docker.CommonContainerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("container.mysql")
public class MySQLProperties extends CommonContainerProperties {
  public static final String BEAN_NAME_CONTAINER_MYSQL = "containerMySQL";
  private String username = "test";
  private String password = "test";
  private String database = "test_db";
  private String host = "localhost";
  private int port = 3306;

  @Override
  public String getDefaultDockerImage() {
    return "vasatanasov/mysql-base:u20.04-8.0";
  }
}
