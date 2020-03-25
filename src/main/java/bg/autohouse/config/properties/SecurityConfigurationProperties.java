package bg.autohouse.config.properties;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConfigurationProperties {
  @Value("${security.jwt.secret}")
  private String jwtSecret;

  @Value("${security.jwt.expiration}")
  private Long expirationTime;

  @Value("${security.jwt.reset.pawssord.expiration}")
  private Long passwordRestExpirationTime;

  public byte[] getJwtSecret() {
    return jwtSecret.getBytes();
  }

  public Long getExpirationTime() {
    return expirationTime;
  }

  public Long getPasswordRestExpirationTime() {
    return passwordRestExpirationTime;
  }
}
