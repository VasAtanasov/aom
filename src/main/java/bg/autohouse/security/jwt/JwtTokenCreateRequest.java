package bg.autohouse.security.jwt;

import bg.autohouse.data.models.User;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
public class JwtTokenCreateRequest {
  private JwtTokenType tokenType;
  @Setter private Long shortExpiryMillis;
  @Setter private Map<String, Object> claims = new HashMap<>();
  @Setter private Map<String, Object> headerParameters = new HashMap<>();

  public JwtTokenCreateRequest(JwtTokenType tokenType) {
    this.tokenType = tokenType;
    claims.put(JwtTokenProvider.TYPE_KEY, tokenType);
  }

  public JwtTokenCreateRequest(JwtTokenType tokenType, User user) {
    this(
        tokenType,
        user.getId(),
        user.getUsername(),
        user.getAuthorities().stream()
            .map(role -> role.toString())
            .collect(Collectors.joining(",")));
  }

  public JwtTokenCreateRequest(
      JwtTokenType tokenType, String userId, String username, String roles) {
    this(tokenType);
    claims.put(JwtTokenProvider.USER_UID_KEY, userId);
    claims.put(JwtTokenProvider.USER_USERNAME_KEY, username);
    claims.put(JwtTokenProvider.SYSTEM_ROLE_KEY, roles);
  }

  public void addClaim(String key, String value) {
    claims.put(key, value);
  }
}
