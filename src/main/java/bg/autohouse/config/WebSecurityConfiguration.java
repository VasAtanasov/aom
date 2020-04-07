package bg.autohouse.config;

import bg.autohouse.security.jwt.JwtAuthenticationEntryPoint;
import bg.autohouse.security.jwt.JwtAuthenticationFilter;
import bg.autohouse.security.jwt.JwtAuthorizationFilter;
import bg.autohouse.security.jwt.JwtTokenProvider;
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

  private static final String[] PUBLIC_POST_URLS =
      new String[] {
        "/api//users/register",
        "/api/users/password-reset-request",
        "/api/users/password-reset",
        "/api/vehicles/offers/search"
      };

  private static final String[] PUBLIC_GET_URLS =
      new String[] {
        "/api/users/email-verification", "/api/vehicles/state", "/api/vehicles/makers/**"
      };

  public static final String H2_CONSOLE = "/h2-console/**";
  private final JwtAuthenticationEntryPoint unauthorizedHandler;
  private final JwtTokenProvider tokenProvider;
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
        .antMatchers(HttpMethod.POST, PUBLIC_POST_URLS)
        .permitAll()
        .antMatchers(HttpMethod.GET, PUBLIC_GET_URLS)
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
