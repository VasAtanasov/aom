package bg.autohouse.config.properties;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConfigurationProperties {
  @Value("${security.jwt.secret}")
  private String jwtSecret;

  public byte[] getJwtSecret() {
    return jwtSecret.getBytes();
  }
}
