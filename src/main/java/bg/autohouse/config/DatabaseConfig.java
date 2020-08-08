package bg.autohouse.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(dbUrl + "createDatabaseIfNotExist=TRUE&allowPublicKeyRetrieval=true&useSSL=FALSE&serverTimezone=UTC&useUnicode=TRUE&characterEncoding=utf-8&autoReconnect=TRUE&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true&useServerPrepStmts=true&cachePrepStmts=true&prepStmtCacheSize=250&prepStmtCacheSqlLimit=2048");
    return new HikariDataSource(config);
  }
}
