package bg.autohouse.security.jwt;

import bg.autohouse.data.models.enums.Role;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtBasedAuthentication extends AbstractAuthenticationToken {

  private static final long serialVersionUID = -86727903559064094L;
  @Getter private String token;
  @Getter private String userId;
  private UserDetails userDetails;

  public JwtBasedAuthentication(
      Collection<Role> authorities, String token, String userId, UserDetails userDetails) {
    super(authorities);
    this.token = token;
    this.userId = userId;
    this.userDetails = userDetails;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return userDetails;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }
}
