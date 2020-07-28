package bg.autohouse.data.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROOT,
  ADMIN,
  USER;

  @Override
  public String getAuthority() {
    return name();
  }
}
