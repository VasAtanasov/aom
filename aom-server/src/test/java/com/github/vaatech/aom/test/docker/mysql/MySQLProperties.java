package com.github.vaatech.aom.test.docker.mysql;

import com.github.vaatech.aom.test.docker.CommonContainerProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties("container.mysql")
public class MySQLProperties extends CommonContainerProperties {
  public static final String BEAN_NAME_CONTAINER_MYSQL = "containerMySQL";
  private String username = "test";
  private String password = "test";
  private String database = "test_db";
  private String host = "localhost";
  private int port = 3306;

  @Override
  public DockerImage getDefaultDockerImage() {
    return new DockerImage("vasatanasov", "mysql-base", "u20.04-8.0");
  }
}
