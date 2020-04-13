package bg.autohouse.security.jwt;

import bg.autohouse.data.models.User;
import bg.autohouse.util.UIDGenerator;
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
    String tokenUid = UIDGenerator.generateId();
    claims.put(JwtTokenService.JWT_TYPE_KEY, tokenType);
    claims.put(JwtTokenService.JWT_UID, tokenUid);
  }

  public JwtTokenCreateRequest(JwtTokenType tokenType, User user) {
    this(tokenType);
    claims.put(JwtTokenService.USER_UID_KEY, user.getId());
    claims.put(JwtTokenService.USER_USERNAME_KEY, user.getUsername());
    claims.put(
        JwtTokenService.ROLE_KEY,
        user.getAuthorities().stream()
            .map(role -> role.toString())
            .collect(Collectors.joining(",")));
  }

  public JwtTokenCreateRequest(JwtTokenType tokenType, String username) {
    this(tokenType);
    claims.put(JwtTokenService.USER_USERNAME_KEY, username);
  }

  public void addClaim(String key, String value) {
    claims.put(key, value);
  }
}
