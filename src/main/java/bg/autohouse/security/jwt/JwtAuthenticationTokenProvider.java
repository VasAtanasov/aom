package bg.autohouse.security.jwt;

import bg.autohouse.config.properties.SecurityConfigurationProperties;
import bg.autohouse.data.models.User;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtAuthenticationTokenProvider {
  @Autowired private SecurityConfigurationProperties securityConfigurationProperties;

  public String generateToken(User user) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + securityConfigurationProperties.getExpirationTime());

    String userId = user.getId().toString();
    String roles =
        user.getAuthorities().stream()
            .map(role -> role.toString())
            .collect(Collectors.joining(","));

    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userId);
    claims.put("roles", roles);
    claims.put("username", user.getUsername());

    return Jwts.builder()
        .setSubject(userId)
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, securityConfigurationProperties.getJwtSecret())
        .compact();
  }

  public JwtAuthenticationToken generateTokenEntity(
      User user, JwtAuthenticationTokenType tokenType) {

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + securityConfigurationProperties.getExpirationTime());

    String userId = user.getId().toString();
    String roles =
        user.getAuthorities().stream()
            .map(role -> role.toString())
            .collect(Collectors.joining(","));

    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userId);
    claims.put("roles", roles);
    claims.put("username", user.getUsername());

    String token =
        Jwts.builder()
            .setSubject(userId)
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, securityConfigurationProperties.getJwtSecret())
            .compact();

    return new JwtAuthenticationToken(
        token, tokenType, user.getUsername(), securityConfigurationProperties.getExpirationTime());
  }

  // private String getTokenString()

  public String getUserIdFromJWT(String token) {
    Claims claims = extractClaims(token);
    return claims.get("id", String.class);
  }

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

  private Claims extractClaims(String token) {
    return parseToken(token).getBody();
  }

  private Jws<Claims> parseToken(String token) {
    return Jwts.parser()
        .setSigningKey(securityConfigurationProperties.getJwtSecret())
        .parseClaimsJws(token);
  }
}
