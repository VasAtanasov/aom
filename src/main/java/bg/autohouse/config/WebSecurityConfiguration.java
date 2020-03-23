package bg.autohouse.config;

import bg.autohouse.security.jwt.JwtAuthenticationEntryPoint;
import bg.autohouse.security.jwt.JwtAuthenticationFilter;
import bg.autohouse.security.jwt.JwtAuthenticationTokenProvider;
import bg.autohouse.security.jwt.JwtAuthorizationFilter;
import bg.autohouse.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

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
  public static final String APP_MAKERS_URL = "/api/vehicles/makers/**";
  public static final String REGISTER_URL = "/api//users/register";
  public static final String VERIFICATION_EMAIL_URL = "/api/users/email-verification";
  public static final String PASSWORD_RESET_REQUEST_URL = "/api/users/password-reset-request";
  public static final String PASSWORD_RESET_URL = "/api/users/password-reset";
  public static final String H2_CONSOLE = "/h2-console/**";

  private final JwtAuthenticationEntryPoint unauthorizedHandler;
  private final JwtAuthenticationTokenProvider tokenProvider;
  private final UserService userService;
  private final PasswordEncoder encoder;

  @Override
  protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
    authBuilder.userDetailsService(userService).passwordEncoder(encoder);
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
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, REGISTER_URL)
        .permitAll()
        .antMatchers(HttpMethod.GET, VERIFICATION_EMAIL_URL)
        .permitAll()
        .antMatchers(HttpMethod.POST, PASSWORD_RESET_REQUEST_URL)
        .permitAll()
        .antMatchers(HttpMethod.POST, PASSWORD_RESET_URL)
        .permitAll()
        .antMatchers(HttpMethod.GET, APP_STATE_URL)
        .permitAll()
        .antMatchers(HttpMethod.GET, APP_MAKERS_URL)
        .permitAll()
        .antMatchers(H2_CONSOLE)
        .permitAll()
        .antMatchers(COMMON_PATTERNS)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilter(getAuthenticationFilter())
        .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService, tokenProvider))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.headers().frameOptions().disable();
  }

  protected JwtAuthenticationFilter getAuthenticationFilter() throws Exception {
    final JwtAuthenticationFilter filter =
        new JwtAuthenticationFilter(authenticationManager(), tokenProvider);
    return filter;
  }
}
