package bg.autohouse.security.jwt;

import static bg.autohouse.security.jwt.JwtTokenSpecifications.*;

import bg.autohouse.config.properties.SecurityConfigurationProperties;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.TimeUtils;
import bg.autohouse.web.enums.RestMessage;
import io.jsonwebtoken.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtTokenServiceImpl implements JwtTokenService {
  private static final String INVALID_TOKEN_MESSAGE = RestMessage.INVALID_TOKEN.name();

  private final SecurityConfigurationProperties securityProperties;
  private final JwtTokenRepository tokenBlackList;
  private String keyIdentifier;

  @PostConstruct
  public void init() {
    keyIdentifier = UUID.randomUUID().toString();
    log.debug("created KUID for main platform: {}", keyIdentifier);
  }

  @Override
  public String createJwt(JwtTokenCreateRequest request) {
    request.setShortExpiryMillis(securityProperties.getExpirationTime());
    Date now = TimeUtils.now();
    long typeExpiryMillis = convertTypeToExpiryMillis(request.getTokenType());
    Date expiryDate = TimeUtils.dateOf(now.getTime() + typeExpiryMillis);
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
      case API_CLIENT:
        return securityProperties.getExpirationTime();
      default:
        return Duration.ofMinutes(5L).toMillis();
    }
  }

  @Override
  public String getUserIdFromJWT(String token) {
    return (String) getClaimFromToken(token, claims -> claims.get(USER_UID_KEY));
  }

  @Override
  public String getJwtUidFromJWT(String token) {
    return (String) getClaimFromToken(token, claims -> claims.get(JWT_UID));
  }

  @Override
  public String getUsernameFromJWT(String token) {
    return (String) getClaimFromToken(token, claims -> claims.get(USER_USERNAME_KEY));
  }

  @Override
  public String getTokenTypeFromJWT(String token) {
    return (String) getClaimFromToken(token, claims -> claims.get(JWT_TYPE_KEY));
  }

  @Override
  public List<String> getRolesFromJwtToken(String token) {
    String joinedRoles = (String) getClaimFromToken(token, claims -> claims.get(ROLE_KEY));
    return Assert.has(joinedRoles) ? Arrays.asList(joinedRoles.split(",")) : new ArrayList<>();
  }

  @Override
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
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
      log.error("Unexpected token validation error.", e.getMessage());
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

  @Override
  public boolean isValidTokenType(String token, JwtTokenType expectType) {
    if (!Assert.has(token)) return false;
    if (!Assert.has(expectType)) return false;
    final String tokenTypeString = getTokenTypeFromJWT(token);
    if (!Assert.has(tokenTypeString)) return false;
    JwtTokenType tokenType = EnumUtils.fromString(tokenTypeString, JwtTokenType.class).orElse(null);
    if (!Assert.has(expectType)) return false;
    return expectType.equals(tokenType);
  }

  private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    try {
      final Claims claims = extractClaims(token);
      return claimsResolver.apply(claims);
    } catch (Exception e) {
      log.error("Failed to get claim from token");
      return null;
    }
  }

  private Claims extractClaims(String token) {
    return Jwts.parser()
        .setSigningKey(securityProperties.getJwtSecret())
        .parseClaimsJws(token)
        .getBody();
  }

  @Override
  @Transactional
  public String refreshToken(String oldToken, JwtTokenType jwtType) {
    boolean isTokenStillValid = false;
    Date expirationTime = null;
    String newToken = null;
    String userId = null;
    String username = null;
    String roles = null;
    try {
      userId = getUserIdFromJWT(oldToken);
      username = getUsernameFromJWT(oldToken);
      roles = (String) getClaimFromToken(oldToken, claims -> claims.get(ROLE_KEY));
      isTokenStillValid = !isBlackListed(oldToken);
    } catch (ExpiredJwtException e) {
      log.error("Token validation failed. The token is expired.", e);
      expirationTime = e.getClaims().getExpiration();
    }
    if (isTokenStillValid || expirationTime != null && expirationTime.before(TimeUtils.now())) {
      JwtTokenCreateRequest cjtRequest =
          new JwtTokenCreateRequest(jwtType, userId, username, roles);
      blackListJwt(oldToken);
      newToken = createJwt(cjtRequest);
    }
    return newToken;
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isBlackListed(String token) {
    String tokenUid = getJwtUidFromJWT(token);
    Assert.notNull(tokenUid, INVALID_TOKEN_MESSAGE);
    String username = getUsernameFromJWT(token);
    Assert.notNull(username, INVALID_TOKEN_MESSAGE);
    return tokenBlackList.findOne(forUser(username).and(withTokenUid(tokenUid))).isPresent();
  }

  @Override
  @Transactional
  public void blackListJwt(String token) {
    Assert.notNull(token, INVALID_TOKEN_MESSAGE);
    JwtTokenType tokenType =
        EnumUtils.fromString(getTokenTypeFromJWT(token), JwtTokenType.class).orElse(null);
    Assert.notNull(tokenType, INVALID_TOKEN_MESSAGE);
    String tokenUid = getJwtUidFromJWT(token);
    Assert.notNull(tokenUid, INVALID_TOKEN_MESSAGE);
    String userId = getUserIdFromJWT(token);
    Assert.notNull(userId, INVALID_TOKEN_MESSAGE);
    String username = getUsernameFromJWT(token);
    Assert.notNull(username, INVALID_TOKEN_MESSAGE);
    Date expirationTime = getExpirationDateFromToken(token);
    Assert.notNull(expirationTime, INVALID_TOKEN_MESSAGE);
    JwtToken invalidToken =
        JwtToken.builder()
            .value(token)
            .tokenUid(tokenUid)
            .type(tokenType)
            .username(username)
            .userId(userId)
            .expirationTime(expirationTime)
            .build();
    tokenBlackList.save(invalidToken);
  }
}
