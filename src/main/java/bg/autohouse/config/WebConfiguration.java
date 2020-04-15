package bg.autohouse.config;

import bg.autohouse.web.interseptors.SimpleLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  public static final String APP_V1_MEDIA_TYPE_JSON = "application/bg.autohouse.api-v1+json";

  public static final String URL_INDEX = "/";
  public static final String URL_API_BASE = "/api";

  public static final String URL_ADMIN_BASE = "/admin";
  public static final String URL_ADMIN_USERS = URL_ADMIN_BASE + "/users";

  public static final String URL_VALIDATION = "/validation";

  public static final String URL_USER_BASE = "/users";
  public static final String URL_ACCOUNT_BASE = "/accounts";
  public static final String URL_USER_AUTH = "/auth";
  public static final String URL_USER_REGISTER = "/register";
  public static final String URL_USER_REGISTER_EMAIL_VERIFICATION = URL_USER_REGISTER + "/verify";
  public static final String URL_USER_LOGIN_OR_REGISTER = "/login-or-register";
  public static final String URL_USER_LOGIN = "/login";
  public static final String URL_USER_LOGOUT = "/logout";
  public static final String URL_USER_HOME = "/home";
  public static final String URL_USER_PROFILE = "/profile";
  public static final String URL_TOKEN_VALIDATE = "/token/validate";
  public static final String URL_TOKEN_REFRESH = "/token/refresh";

  public static final String URL_PASSWORD_RESET_REQUEST = "/password-reset-request";
  public static final String URL_PASSWORD_RESET_COMPLETE = "/password-reset-complete";
  public static final String URL_PASSWORD_RESET_VALIDATE = "/reset-password-validate";

  public static final String URL_VEHICLES = "/vehicles";
  public static final String URL_MAKERS = URL_VEHICLES + "/makers";
  public static final String URL_MODELS = URL_VEHICLES + "/models";
  public static final String OFFERS = URL_VEHICLES + "/offers";

  private static final long MAX_AGE_SECS = 3600;

  @Bean
  public SimpleLoggingInterceptor loggingInterceptor() {
    return new SimpleLoggingInterceptor();
  }

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
        .maxAge(MAX_AGE_SECS);
  }

  @Override
  public void configurePathMatch(final PathMatchConfigurer configurer) {
    configurer.addPathPrefix(
        URL_API_BASE, HandlerTypePredicate.forAnnotation(RestController.class));
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loggingInterceptor());
  }
}
