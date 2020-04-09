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
public class JwtTokenProviderImpl implements JwtTokenProvider {
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

    long typeExpiryMillis = convertTypeToExpiryMillis(request.getTokenType());
    Date now = new Date();
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
        return Duration.ofMinutes(7L).toMillis();
      case RESET:
      case VALIDATION:
        return Duration.ofMinutes(5).toMillis();
      case VERIFICATION:
        return securityProperties.getExpirationTime(); // now long lived
      default:
        return 1L;
    }
  }

  public JwtToken createJwtEntity(JwtTokenCreateRequest request) {
    String token = createJwt(request);
    return new JwtToken(
        token,
        request.getTokenType(),
        extractFromToken(JwtTokenProvider.USER_USERNAME_KEY, token),
        extractFromToken(JwtTokenProvider.USER_UID_KEY, token),
        extractClaims(token).getExpiration());
  }

  @Override
  public String getUserIdFromJWT(String token) {
    return extractFromToken(JwtTokenProvider.USER_UID_KEY, token);
  }

  @Override
  public String getUsernameFromJWT(String token) {
    return extractFromToken(JwtTokenProvider.USER_USERNAME_KEY, token);
  }

  @Override
  public String getTokenTypeFromJWT(String token) {
    return extractFromToken(JwtTokenProvider.TYPE_KEY, token);
  }

  @Override
  public List<String> getRolesFromJwtToken(String token) {
    String joinedRoles = extractClaims(token).get(SYSTEM_ROLE_KEY, String.class);
    return Assert.has(joinedRoles) ? Arrays.asList(joinedRoles.split(",")) : new ArrayList<>();
  }

  @Override
  public boolean validateToken(String authToken) {
    try {
      parseToken(authToken);
      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }

  @Override
  public boolean hasTokenExpired(String token) {
    try {
      Claims claims = extractClaims(token);
      Date tokenExpirationDate = claims.getExpiration();
      Date todayDate = new Date();
      return tokenExpirationDate.before(todayDate);
    } catch (ExpiredJwtException ex) {
      log.error("The token is expired.", ex);
      return true;
    } catch (Exception ex) {
      log.error("Unexpected token validation error.", ex);
      return true;
    }
  }

  @Override
  public String refreshToken(String oldToken, JwtTokenType jwtType) {
    boolean isTokenStillValid = false;
    Date expirationTime = null;
    String newToken = null;
    String userId = null;
    String username = null;
    String roles = null;

    try {
      Jws<Claims> jwt = parseToken(oldToken);
      userId = jwt.getBody().get(USER_UID_KEY, String.class);
      username = jwt.getBody().get(USER_USERNAME_KEY, String.class);
      roles = jwt.getBody().get(SYSTEM_ROLE_KEY, String.class);
      isTokenStillValid = true;
    } catch (ExpiredJwtException e) {
      log.error("Token validation failed. The token is expired.", e);
      expirationTime = e.getClaims().getExpiration();
    }
    if (isTokenStillValid || expirationTime != null && expirationTime.before(new Date())) {
      JwtTokenCreateRequest cjtRequest =
          new JwtTokenCreateRequest(jwtType, userId, username, roles);

      newToken = createJwt(cjtRequest);
    }

    return newToken;
  }

  private String extractFromToken(String key, String token) {
    try {
      Claims claims = extractClaims(token);
      return claims.get(key, String.class);
    } catch (Exception e) {
      log.error("Failed to get claim key from jwt token: {}", e.getMessage());
      return null;
    }
  }

  private Claims extractClaims(String token) {
    return parseToken(token).getBody();
  }

  private Jws<Claims> parseToken(String token) {
    try {
      return Jwts.parser().setSigningKey(securityProperties.getJwtSecret()).parseClaimsJws(token);
    } catch (Exception e) {
      log.error("Failed to get user id from jwt token: {}", e.getMessage());
      return null;
    }
  }
}
