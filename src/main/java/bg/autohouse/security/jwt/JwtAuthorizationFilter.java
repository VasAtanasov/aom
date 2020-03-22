package bg.autohouse.security.jwt;

import bg.autohouse.security.SecurityConstants;
import bg.autohouse.service.services.UserService;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
  private final UserService userService;
  private final JwtAuthenticationTokenProvider tokenProvider;

  public JwtAuthorizationFilter(
      AuthenticationManager authManager,
      UserService userService,
      JwtAuthenticationTokenProvider tokenProvider) {
    super(authManager);
    this.userService = userService;
    this.tokenProvider = tokenProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = getJwtFromRequest(request);

      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

        String userId = tokenProvider.getUserIdFromJWT(jwt);

        UserDetails userDetails = userService.loadUserById(userId);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception ex) {
      log.error("Could not set user authentication in security context", ex);
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
    if (StringUtils.hasText(bearerToken)
        && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
