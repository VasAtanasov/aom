package bg.autohouse.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.sql.DataSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class BeanConfiguration {
  private static ModelMapper mapper;

  static {
    mapper = new ModelMapper();
  }

  @Bean
  public ModelMapper modelMapper() {
    mapper
        .getConfiguration()
        .setAmbiguityIgnored(true)
        .setFieldAccessLevel(AccessLevel.PRIVATE)
        .setFieldMatchingEnabled(true);
    return mapper;
  }

  @Bean
  public Gson gson() {
    return new GsonBuilder().setPrettyPrinting().create();
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  public JdbcTemplate getTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
