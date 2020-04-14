package bg.autohouse.security.jwt;

import java.util.Date;
import java.util.List;

public interface JwtTokenService {
  String USER_UID_KEY = "USER_UID ";
  String ROLE_KEY = "ROLE";
  String USER_USERNAME_KEY = "USER_USERNAME";
  String JWT_TYPE_KEY = "JWT_TYPE";
  String JWT_UID = "JWT_UID";

  String getUserIdFromJWT(String token);

  String getJwtUidFromJWT(String token);

  String getUsernameFromJWT(String token);

  String getTokenTypeFromJWT(String token);

  List<String> getRolesFromJwtToken(String token);

  Date getExpirationDateFromToken(String token);

  String createJwt(JwtTokenCreateRequest request);

  boolean isJwtTokenValid(String token);

  boolean isJwtTokenExpired(String token);

  boolean isValidTokenType(String token, JwtTokenType auth);

  String refreshToken(String oldToken, JwtTokenType jwtType);

  boolean isBlackListed(String token);

  void blackListJwt(String token);
}
