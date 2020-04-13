package bg.autohouse.config;

import bg.autohouse.common.Constants;
import bg.autohouse.config.properties.SecurityConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@PropertySource("classpath:configuration/security.properties")
@ComponentScan(basePackages = Constants.SECURITY_BASE_PACKAGE)
public class SecurityConfiguration {

  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityConfigurationProperties securityConfigurationProperties() {
    return new SecurityConfigurationProperties();
  }
}
