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
  public static final String URL_API_BASE = "/api/vehicles";

  public static final String URL_ADMIN_BASE = "/admin";
  public static final String URL_ADMIN_USERS = URL_ADMIN_BASE + "/users";

  private static final String URL_USER_BASE = "/user";
  public static final String URL_USER_REGISTER = URL_USER_BASE + "/register";
  public static final String URL_USER_LOGIN = URL_USER_BASE + "/login";
  public static final String URL_USER_LOGOUT = URL_USER_BASE + "/logout";
  public static final String URL_USER_HOME = URL_USER_BASE + "/home";
  public static final String URL_USER_PROFILE = URL_USER_BASE + "/profile";

  public static final String URL_MAKERS = "/makers";
  public static final String OFFERS = "/offers";

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
