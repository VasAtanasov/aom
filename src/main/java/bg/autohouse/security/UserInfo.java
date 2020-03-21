package bg.autohouse.security;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import bg.autohouse.data.models.User;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

@Getter
public class UserInfo extends org.springframework.security.core.userdetails.User {

  private final String userId;
  private final User.Role role;
  private final String phoneNumber;

  private static final long serialVersionUID = -4399641031546603332L;

  public static UserInfo extractFrom(final Authentication authentication) {
    requireNonNull(authentication, "No authentication available");
    checkState(authentication.isAuthenticated(), "User is not authenticated");

    final Object principal = authentication.getPrincipal();
    requireNonNull(principal, "No principal for authentication");

    if (principal instanceof UserInfo) {
      return UserInfo.class.cast(principal);
    }
    throw new IllegalStateException(
        "Authenticate user principal type is unknown: " + principal.getClass().getSimpleName());
  }

  public static UserInfo extractFrom(final UserDetails userDetails) {
    requireNonNull(userDetails, "No userDetails");
    if (userDetails instanceof UserInfo) {
      return UserInfo.class.cast(userDetails);
    }
    throw new IllegalStateException(
        "Authenticate user principal type is unknown: " + userDetails.getClass().getSimpleName());
  }

  public static String extractUserIdForEntity(final Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
      if (authentication.getPrincipal() == null) {
        return null;

      } else if (authentication.getPrincipal() instanceof UserInfo) {
        final String userId = extractFrom(authentication).getUserId();

        // This check is needed when database is empty and there exists no
        // users at all initially.
        return userId != null ? userId : null;
      }

      return null;
    }

    return null;
  }

  public static class UserInfoBuilder {
    private String username;
    private String password;
    private String userId;
    private String phoneNumber;
    private boolean active;
    private User.Role role;

    public UserInfoBuilder(final String username, final String userId, final User.Role role) {
      this.username = username;
      this.userId = userId;
      this.role = role;
      this.password = RandomStringUtils.random(32);
    }

    public UserInfoBuilder(final User user) {
      this.username = user.getUsername();
      this.password = user.getPassword();
      this.userId = user.getId();
      this.phoneNumber = user.getPhoneNumber();
      this.active = user.isEnabled();
      this.role = user.getRole();
    }

    private List<GrantedAuthority> getAuthorities() {
      return role == null
          ? Collections.emptyList()
          : AuthorityUtils.createAuthorityList(role.name());
    }

    public Authentication createAuthentication() {
      return new PreAuthenticatedAuthenticationToken(new UserInfo(this), null, getAuthorities());
    }

    public UserInfo createUserInfo() {
      Assert.hasText(this.username, "username is empty");
      return new UserInfo(this);
    }
  }

  private UserInfo(final UserInfoBuilder builder) {
    super(
        builder.username,
        builder.password,
        builder.active,
        builder.active,
        builder.active,
        builder.active,
        builder.getAuthorities());
    this.role = requireNonNull(builder.role);
    this.userId = builder.userId;
    this.phoneNumber = builder.phoneNumber;
  }
}
