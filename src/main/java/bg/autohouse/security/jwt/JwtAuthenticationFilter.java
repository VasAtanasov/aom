package bg.autohouse.security.jwt;

import bg.autohouse.data.models.enums.Role;
import bg.autohouse.service.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private UserService userService;
  private JwtTokenService jwtService;

  @Autowired
  public void setJwtService(JwtTokenService jwtService) {
    this.jwtService = jwtService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.debug("request method: {}", request.getMethod());
    if ("OPTIONS".equals(request.getMethod())) { // to handle CORS
      filterChain.doFilter(request, response);
      return;
    }

    AuthorizationHeader authorizationHeader = new AuthorizationHeader(request);
    final String token =
        authorizationHeader.hasBearerToken() ? authorizationHeader.getBearerToken() : null;

    log.debug("auth headers: {}, token: {}", request.getHeaderNames(), token);

    if (authorizationHeader.hasBearerToken() && jwtService.isJwtTokenValid(token)) {
      String userId = jwtService.getUserIdFromJWT(token);
      UserDetails userDetails = userService.loadUserById(userId);
      log.debug("User ID: {}", userId);
      final List<String> standardRolesNames = jwtService.getRolesFromJwtToken(token);
      final Set<Role> standardRoles =
          standardRolesNames.stream().map(Role::valueOf).collect(Collectors.toSet());
      log.debug("and user roles = {}", standardRoles);
      JwtBasedAuthentication auth =
          new JwtBasedAuthentication(standardRoles, token, userId, userDetails);
      SecurityContextHolder.getContext().setAuthentication(auth);
      log.debug("Finished pushing authentication object");
    } else {
      SecurityContextHolder.getContext()
          .setAuthentication(new AnonAuthentication()); // to avoid redirects etc
    }

    filterChain.doFilter(request, response);
  }

  // private boolean isValidTokenType(String jwt) {
  //   if (!Assert.has(jwt)) return false;
  //   final String tokenTypeString = jwtService.getTokenTypeFromJWT(jwt);
  //   if (!Assert.has(tokenTypeString)) return false;
  //   JwtTokenType tokenType = EnumUtils.fromString(tokenTypeString,
  // JwtTokenType.class).orElse(null);
  //   if (!Assert.has(tokenType)) return false;
  //   return JwtTokenType.VERIFICATION.equals(tokenType);
  // }
}
