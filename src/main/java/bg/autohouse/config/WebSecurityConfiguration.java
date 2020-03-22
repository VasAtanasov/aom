package bg.autohouse.config;

import bg.autohouse.security.jwt.JwtAuthenticationEntryPoint;
import bg.autohouse.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
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

  public static final String APP_STATE_URL = "/api/vehicles/state";
  public static final String SIGN_UP_URL = "/api//users";
  public static final String VERIFICATION_EMAIL_URL = "/api/users/email-verification";
  public static final String PASSWORD_RESET_REQUEST_URL = "/api/users/password-reset-request";
  public static final String PASSWORD_RESET_URL = "/api/users/password-reset";
  public static final String H2_CONSOLE = "/h2-console/**";

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthenticationEntryPoint unauthorizedHandler;

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers(COMMON_PATTERNS)
        .permitAll()
        .antMatchers("/api/auth/**")
        .permitAll();

    http.headers().frameOptions().disable();
  }
}
