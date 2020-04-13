package bg.autohouse.security.jwt;

import bg.autohouse.config.properties.SecurityConfigurationProperties;
import bg.autohouse.util.Assert;
import io.jsonwebtoken.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtTokenServiceImpl implements JwtTokenService {
  @Autowired private SecurityConfigurationProperties securityProperties;
  private String keyIdentifier;

  @PostConstruct
  public void init() {
    keyIdentifier = UUID.randomUUID().toString();
    log.debug("created KUID for main platform: {}", keyIdentifier);
  }

  @Override
  public String createJwt(JwtTokenCreateRequest request) {
    request.setShortExpiryMillis(securityProperties.getExpirationTime());

    Date now = new Date();
    long typeExpiryMillis = convertTypeToExpiryMillis(request.getTokenType());
    Date expiryDate = new Date(now.getTime() + typeExpiryMillis);
    request.getHeaderParameters().put("kid", keyIdentifier);

    return Jwts.builder()
        .setHeaderParams(request.getHeaderParameters())
        .setClaims(request.getClaims())
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, securityProperties.getJwtSecret())
        .compact();
  }

  private long convertTypeToExpiryMillis(JwtTokenType jwtType) {

    switch (jwtType) {
      case REGISTRATION:
        return Duration.ofMinutes(5L).toMillis();
      case RESET:
      case VALIDATION:
      case VERIFICATION:
        return Duration.ofMinutes(10L).toMillis();
      case AUTH:
        return securityProperties.getExpirationTime(); // now long lived
      default:
        return 1L;
    }
  }

  @Override
  public String getUserIdFromJWT(String token) {
    return extractFromToken(JwtTokenService.USER_UID_KEY, token);
  }

  @Override
  public String getUsernameFromJWT(String token) {
    return extractFromToken(JwtTokenService.USER_USERNAME_KEY, token);
  }

  @Override
  public String getTokenTypeFromJWT(String token) {
    return extractFromToken(JwtTokenService.JWT_TYPE_KEY, token);
  }

  @Override
  public List<String> getRolesFromJwtToken(String token) {
    String joinedRoles = extractClaims(token).get(ROLE_KEY, String.class);
    return Assert.has(joinedRoles) ? Arrays.asList(joinedRoles.split(",")) : new ArrayList<>();
  }

  @Override
  public boolean isJwtTokenValid(String token) {
    try {
      Jwts.parser().setSigningKey(securityProperties.getJwtSecret()).parse(token);
      return true;
    } catch (ExpiredJwtException e) {
      log.error("Token validation failed. The token is expired. Exception: {}", e.getMessage());
      return false;
    } catch (SignatureException e) {
      log.error("Token validation failed, wrong signature. Exception: {}", e.getMessage());
      return false;
    } catch (Exception e) {
      log.error("Unexpected token validation error.", e);
      return false;
    }
  }

  @Override
  public boolean isJwtTokenExpired(String token) {
    try {
      Jwts.parser().setSigningKey(securityProperties.getJwtSecret()).parse(token).getBody();
      return false;
    } catch (ExpiredJwtException e) {
      log.error("The token is expired.", e);
      return true;
    } catch (Exception e) {
      log.error("Unexpected token validation error.", e);
      return false;
    }
  }

  // @Override
  // public String refreshToken(String oldToken, JwtTokenType jwtType) {
  //   boolean isTokenStillValid = false;
  //   Date expirationTime = null;
  //   String newToken = null;
  //   String userId = null;
  //   String username = null;
  //   String roles = null;

  //   try {
  //     Jws<Claims> jwt = parseToken(oldToken);
  //     userId = jwt.getBody().get(USER_UID_KEY, String.class);
  //     username = jwt.getBody().get(USER_USERNAME_KEY, String.class);
  //     roles = jwt.getBody().get(ROLE_KEY, String.class);
  //     isTokenStillValid = true;
  //   } catch (ExpiredJwtException e) {
  //     log.error("Token validation failed. The token is expired.", e);
  //     expirationTime = e.getClaims().getExpiration();
  //   }
  //   if (isTokenStillValid || expirationTime != null && expirationTime.before(new Date())) {
  //     JwtTokenCreateRequest cjtRequest =
  //         new JwtTokenCreateRequest(jwtType, userId, username, roles);

  //     newToken = createJwt(cjtRequest);
  //   }

  //   return newToken;
  // }

  private String extractFromToken(String key, String token) {
    try {
      Claims claims =
          Jwts.parser()
              .setSigningKey(securityProperties.getJwtSecret())
              .parseClaimsJws(token)
              .getBody();
      return claims.get(key, String.class);
    } catch (Exception e) {
      log.error("Failed to get {} from jwt token: {}", key, e.getMessage());
      return null;
    }
  }

  private Claims extractClaims(String token) {
    return Jwts.parser()
        .setSigningKey(securityProperties.getJwtSecret())
        .parseClaimsJws(token)
        .getBody();
  }

  // private Jws<Claims> parseToken(String token) {
  //   try {
  //     return
  // Jwts.parser().setSigningKey(securityProperties.getJwtSecret()).parseClaimsJws(token);
  //   } catch (Exception e) {
  //     log.error("Failed to get user id from jwt token: {}", e.getMessage());
  //     return null;
  //   }
  // }
}
