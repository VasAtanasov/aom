package com.github.vaatech.aom.test.docker.mysql;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.utility.DockerImageName;

public class MySQLContainer<SELF extends MySQLContainer<SELF>> extends JdbcDatabaseContainer<SELF> {
  static final String DEFAULT_USER = "test";

  static final String DEFAULT_PASSWORD = "test";

  public static final Integer MYSQL_PORT = 3306;

  private String username = DEFAULT_USER;

  private String password = DEFAULT_PASSWORD;

  private static final String MYSQL_ROOT_USER = "root";

  private String databaseName = "test";

  public MySQLContainer(DockerImageName dockerImageName) {
    super(dockerImageName);

    addExposedPort(MYSQL_PORT);
  }

  @Override
  protected void configure() {
    addEnv("DB_ROOT_PASSWORD", MYSQL_ROOT_USER);
    addEnv("MYSQL_DATABASE", databaseName);
    addEnv("MYSQL_USER", username);
    addEnv("MYSQL_PASSWORD", password);

    setStartupAttempts(3);
  }

  @Override
  public String getDriverClassName() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      return "com.mysql.cj.jdbc.Driver";
    } catch (ClassNotFoundException e) {
      return "com.mysql.jdbc.Driver";
    }
  }

  @Override
  public String getJdbcUrl() {
    String additionalUrlParams = constructUrlParameters("?", "&");
    return "jdbc:mysql://"
        + getHost()
        + ":"
        + getMappedPort(MYSQL_PORT)
        + "/"
        + databaseName
        + additionalUrlParams;
  }

  public String getAdditionalUrlParams() {
    return constructUrlParameters("?", "&");
  }

  @Override
  protected String constructUrlForConnection(String queryString) {
    String url = super.constructUrlForConnection(queryString);

    if (!url.contains("useSSL=")) {
      String separator = url.contains("?") ? "&" : "?";
      url = url + separator + "useSSL=false";
    }

    if (!url.contains("allowPublicKeyRetrieval=")) {
      url = url + "&allowPublicKeyRetrieval=true";
    }

    if (!url.contains("createDatabaseIfNotExist=true")) {
      url = url + "&createDatabaseIfNotExist=true";
    }

    return url;
  }

  @Override
  public String getDatabaseName() {
    return databaseName;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getTestQueryString() {
    return "SELECT 1";
  }

  @Override
  public SELF withDatabaseName(final String databaseName) {
    this.databaseName = databaseName;
    return self();
  }

  @Override
  public SELF withUsername(final String username) {
    this.username = username;
    return self();
  }

  @Override
  public SELF withPassword(final String password) {
    this.password = password;
    return self();
  }
}
