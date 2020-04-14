package bg.autohouse.security.jwt;

import bg.autohouse.service.services.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    AuthorizationHeader authHeader = new AuthorizationHeader(request);
    String token = authHeader.hasBearerToken() ? authHeader.getBearerToken() : null;

    log.debug("auth headers: {}, token: {}", request.getHeaderNames(), token);

    try {
      if (authHeader.hasBearerToken()
          && jwtService.isJwtTokenValid(token)
          && !jwtService.isBlackListed(token)) {

        String userId = jwtService.getUserIdFromJWT(token);
        UserDetails userDetails = userService.loadUserById(userId);
        log.debug("User ID: {}", userId);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("Finished pushing authentication object");

      } else {
        SecurityContextHolder.clearContext();
      }
    } catch (Exception e) {
      log.error("Could not set user authentication in security context");
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}
