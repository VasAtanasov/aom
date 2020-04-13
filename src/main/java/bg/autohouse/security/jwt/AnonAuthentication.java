package bg.autohouse.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AnonAuthentication extends AbstractAuthenticationToken {

  private static final long serialVersionUID = 6970834099965445406L;

  public AnonAuthentication() {
    super(null);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }
}
