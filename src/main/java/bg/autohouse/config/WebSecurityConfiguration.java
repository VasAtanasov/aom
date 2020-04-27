package bg.autohouse.config;

import bg.autohouse.security.jwt.JwtAuthenticationEntryPoint;
import bg.autohouse.security.jwt.JwtAuthenticationFilter;
import bg.autohouse.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSecurityConfiguration {
  private static final String[] COMMON_PATTERNS =
      new String[] {
        "/",
        "/favicon.ico",
        "/**/*.png",
        "/**/*.gif",
        "/**/*.svg",
        "/**/*.jpg",
        "/**/*.html",
        "/**/*.css",
        "/**/*.js",
        "/v2/api-docs",
        "/configuration/**",
        "/swagger*/**",
        "/webjars/**"
      };

  private static final String[] PUBLIC_POST_URLS =
      new String[] {
        "/api/auth/login",
        "/api/auth/register",
        "/api/auth/login-or-register",
        "/api/auth/password-reset-request",
        "/api/vehicles/offers/search"
      };

  private static final String[] PUBLIC_GET_URLS =
      new String[] {
        "/api/auth/register/verify",
        "/api/auth/reset-password-validate",
        "/api/auth/password-reset-complete",
        "/api/auth/token/validate",
        "/api/auth/token/refresh",
        "/api/vehicles/state",
        "/api/vehicles/makers/**",
        "/api/app-health",
        "/api/locations/list"
      };

  public static final String H2_CONSOLE = "/h2-console/**";

  private final JwtAuthenticationEntryPoint unauthorizedHandler;
  private final UserService userService;
  private final PasswordEncoder encoder;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationFilter();
  }

  @Configuration
  public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
        throws Exception {
      authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.cors().and().csrf().disable();
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
      http.authorizeRequests()
          .antMatchers(HttpMethod.POST, PUBLIC_POST_URLS)
          .permitAll()
          .antMatchers(HttpMethod.GET, PUBLIC_GET_URLS)
          .permitAll()
          .antMatchers(H2_CONSOLE)
          .permitAll()
          .antMatchers(COMMON_PATTERNS)
          .permitAll()
          .anyRequest()
          .authenticated();
      http.addFilterBefore(
          jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
      http.headers().frameOptions().disable();
    }
  }
}
