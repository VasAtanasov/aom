package bg.autohouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
  public static final String URL_USER_REGISTER = "/register";
  public static final String URL_USER_REGISTER_EMAIL_VERIFICATION = URL_USER_REGISTER + "/verify";
  public static final String URL_USER_LOGIN_OR_REGISTER = URL_USER_REGISTER + "/login-or-register";
  public static final String URL_USER_LOGIN = "/login";
  public static final String URL_USER_LOGOUT = "/logout";
  public static final String URL_USER_HOME = "/home";
  public static final String URL_USER_PROFILE = "/profile";

  public static final String URL_VEHICLES = "/vehicles";
  public static final String URL_MAKERS = URL_VEHICLES + "/makers";
  public static final String URL_MODELS = URL_VEHICLES + "/models";
  public static final String OFFERS = URL_VEHICLES + "/offers";

  private static final long MAX_AGE_SECS = 3600;

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
}
