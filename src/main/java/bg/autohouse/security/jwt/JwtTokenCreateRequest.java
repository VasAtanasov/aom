package bg.autohouse.security.jwt;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.util.UIDUtil;
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
    String tokenUid = UIDUtil.generateStringUid();
    claims.put(JwtTokenService.JWT_TYPE_KEY, tokenType);
    claims.put(JwtTokenService.JWT_UID, tokenUid);
  }

  public JwtTokenCreateRequest(JwtTokenType tokenType, User user) {
    this(tokenType);
    claims.put(JwtTokenService.USER_UID_KEY, user.getId());
    claims.put(JwtTokenService.USER_USERNAME_KEY, user.getUsername());
    claims.put(
        JwtTokenService.ROLE_KEY,
        user.getRoles().stream().map(Role::getAuthority).collect(Collectors.joining(",")));
  }

  public JwtTokenCreateRequest(JwtTokenType jwtType, String userId, String username, String roles) {
    this(jwtType);
    if (userId != null) claims.put(JwtTokenService.USER_UID_KEY, userId);
    if (username != null) claims.put(JwtTokenService.USER_USERNAME_KEY, username);
    if (roles != null) claims.put(JwtTokenService.ROLE_KEY, roles);
  }

  public void addClaim(String key, String value) {
    claims.put(key, value);
  }
}
