package bg.autohouse.security.jwt;

import bg.autohouse.data.models.enums.Role;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetailsDTO implements UserDetails {

  private static final long serialVersionUID = 1L;
  private final String userId;
  private final Collection<Role> userSystemRoles;

  public JwtUserDetailsDTO(String userId, Collection<Role> userSystemRoles) {
    this.userId = userId;
    this.userSystemRoles = userSystemRoles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return userSystemRoles;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return userId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
