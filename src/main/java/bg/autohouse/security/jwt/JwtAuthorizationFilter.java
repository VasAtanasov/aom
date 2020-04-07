package bg.autohouse.security.jwt;

import bg.autohouse.service.services.UserService;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
  private final UserService userService;
  private final JwtTokenProvider tokenProvider;

  public JwtAuthorizationFilter(
      AuthenticationManager authManager, UserService userService, JwtTokenProvider tokenProvider) {
    super(authManager);
    this.userService = userService;
    this.tokenProvider = tokenProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {

      AuthorizationHeader authorizationHeader = new AuthorizationHeader(request);
      final String jwt =
          authorizationHeader.hasBearerToken() ? authorizationHeader.getBearerToken() : null;
      log.debug("auth headers: {}, token: {}", request.getHeaderNames(), jwt);

      if (authorizationHeader.hasBearerToken() && tokenProvider.validateToken(jwt)) {

        String userId = tokenProvider.getUserIdFromJWT(jwt);
        log.debug("User ID: {}", userId);
        UserDetails userDetails = userService.loadUserById(userId);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("Finished pushing authentication object");
      }
    } catch (Exception ex) {
      log.error("Could not set user authentication in security context", ex);
    }

    filterChain.doFilter(request, response);
  }
}
