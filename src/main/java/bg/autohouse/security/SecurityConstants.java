package bg.autohouse.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {
  public static final long EXPIRATION_TIME = 864000000; // 10 days
  public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; // 1 hour
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SECRET = "jf9i4jgu83nfl0jfu57ejf7";
  public static final String SIGN_UP_URL = "/api//users";
  public static final String VERIFICATION_EMAIL_URL = "/api/users/email-verification";
  public static final String PASSWORD_RESET_REQUEST_URL = "/api/users/password-reset-request";
  public static final String PASSWORD_RESET_URL = "/api/users/password-reset";
  public static final String H2_CONSOLE = "/h2-console/**";

  public static final String ADMIN = "ROLE_ADMIN";

  public static final String USER = "ROLE_USER";

  public static final String ANONYMOUS = "ROLE_ANONYMOUS";
}
