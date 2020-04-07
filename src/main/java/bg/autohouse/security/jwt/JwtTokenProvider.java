package bg.autohouse.security.jwt;

import java.util.List;

public interface JwtTokenProvider {
  String USER_UID_KEY = "USER_UID";
  String SYSTEM_ROLE_KEY = "SYSTEM_ROLE_KEY";
  String USER_USERNAME_KEY = "USER_USERNAME";
  String TYPE_KEY = "TYPE";

  String getUserIdFromJWT(String token);

  boolean validateToken(String authToken);

  boolean hasTokenExpired(String token);

  String createJwt(JwtTokenCreateRequest request);

  JwtToken createJwtEntity(JwtTokenCreateRequest request);

  List<String> getRolesFromJwtToken(String token);

  String refreshToken(String oldToken, JwtTokenType jwtType);
}
