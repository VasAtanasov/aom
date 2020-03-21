package bg.autohouse.config;

import bg.autohouse.common.Constants;
import bg.autohouse.config.properties.SecurityConfigurationProperties;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.AutohousePasswordEncoder;
import bg.autohouse.security.authentication.CustomUserDetailsService;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@PropertySource("classpath:configuration/security.properties")
@ComponentScan(basePackages = Constants.SECURITY_BASE_PACKAGE)
public class SecurityConfiguration {

  @Bean
  public RoleHierarchy roleHierarchy() {
    final RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
    hierarchy.setHierarchy(
        Joiner.on('\n')
            .join(
                new String[] {
                  User.Role.ADMIN.includes(User.Role.USER),
                  User.Role.MODERATOR.includes(User.Role.USER)
                }));
    return hierarchy;
  }

  @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
  public static class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    @Autowired private RoleHierarchy roleHierarchy;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
      final DefaultMethodSecurityExpressionHandler handler =
          new DefaultMethodSecurityExpressionHandler();
      handler.setRoleHierarchy(this.roleHierarchy);
      return handler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
      return super.authenticationManager();
    }
  }

  @Configuration
  public static class AuthenticationManagerConfiguration
      extends GlobalAuthenticationConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
      return new CustomUserDetailsService();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
      authBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
      return new AutohousePasswordEncoder();
    }

    @Bean
    public SecurityConfigurationProperties securityConfigurationProperties() {
      return new SecurityConfigurationProperties();
    }
  }
}
