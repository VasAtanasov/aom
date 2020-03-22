package bg.autohouse.security;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import bg.autohouse.data.models.User;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/** Utility class for Spring Security. */
public final class SecurityUtils {
  private static final AuthenticationTrustResolver AUTH_TRUST_RESOLVER =
      new AuthenticationTrustResolverImpl();

  private SecurityUtils() {}

  public static Optional<String> getCurrentUserLogin() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(
            authentication -> {
              if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getUsername();
              } else if (authentication.getPrincipal() instanceof String) {
                return (String) authentication.getPrincipal();
              }
              return null;
            });
  }

  private static boolean isAuthenticated(final Authentication authentication) {
    return authentication != null
        && authentication.getPrincipal() != null
        && !AUTH_TRUST_RESOLVER.isAnonymous(authentication)
        && authentication.isAuthenticated();
  }

  private static Optional<Authentication> findAuthenticationAuthenticated() {
    return Optional.of(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .filter(SecurityUtils::isAuthenticated);
  }

  public boolean isAuthenticated() {
    return findAuthenticationAuthenticated().isPresent();
  }

  public Optional<User> findActiveUserInfo() {
    return findAuthenticationAuthenticated().map(SecurityUtils::extractFrom);
  }

  public boolean isModeratorOrAdmin() {
    return findActiveUserInfo().map(User::isModeratorOrAdmin).orElse(false);
  }

  public User getActiveUserInfoOrNull() {
    return findActiveUserInfo().orElse(null);
  }

  public String getActiveUsernameOrNull() {
    return findActiveUserInfo().map(User::getUsername).orElse(null);
  }

  public Optional<String> findActiveUserId() {
    return findActiveUserInfo().map(User::getId);
  }

  public static boolean isCurrentUserInRole(String authority) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return Optional.ofNullable(securityContext.getAuthentication())
        .map(
            authentication ->
                authentication.getAuthorities().stream()
                    .anyMatch(
                        grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
        .orElse(false);
  }

  public static User extractFrom(final Authentication authentication) {
    requireNonNull(authentication, "No authentication available");
    checkState(authentication.isAuthenticated(), "User is not authenticated");

    final Object principal = authentication.getPrincipal();
    requireNonNull(principal, "No principal for authentication");

    if (principal instanceof User) {
      return User.class.cast(principal);
    }
    throw new IllegalStateException(
        "Authenticate user principal type is unknown: " + principal.getClass().getSimpleName());
  }

  public static User extractFrom(final UserDetails userDetails) {
    requireNonNull(userDetails, "No userDetails");
    if (userDetails instanceof User) {
      return User.class.cast(userDetails);
    }
    throw new IllegalStateException(
        "Authenticate user principal type is unknown: " + userDetails.getClass().getSimpleName());
  }

  public static String extractUserIdForEntity(final Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
      if (authentication.getPrincipal() == null) {
        return null;

      } else if (authentication.getPrincipal() instanceof User) {
        final String userId = extractFrom(authentication).getId();

        // This check is needed when database is empty and there exists no
        // users at all initially.
        return userId != null ? userId : null;
      }

      return null;
    }

    return null;
  }
}
