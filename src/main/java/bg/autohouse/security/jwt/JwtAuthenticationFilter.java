package bg.autohouse.security.jwt;

import bg.autohouse.service.services.UserService;
import bg.autohouse.util.Assert;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  public static final String HEADER_AUTHORIZATION = "Authorization";
  public static final String AUTHORIZATION_PREFIX = "Bearer ";

  private final JwtAuthenticationTokenProvider tokenProvider;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    log.debug("request method: {}", request.getMethod());
    if ("OPTIONS".equals(request.getMethod())) { // to handle CORS
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = parseToken(request);

    if (Assert.has(jwt) && tokenProvider.validateToken(jwt)) {
      try {
        String userId = tokenProvider.getUserIdFromJWT(jwt);
        log.debug("User ID: {}", userId);
        UserDetails userDetails = userService.loadUserById(userId);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception ex) {
        log.error("Could not set user authentication in security context", ex);
        SecurityContextHolder.clearContext();
      }

      try {
        filterChain.doFilter(request, response);
      } finally {
        // Always use temporary JWT authentication
        SecurityContextHolder.clearContext();
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }

  private static String parseToken(final HttpServletRequest request) {
    final String header = request.getHeader(HEADER_AUTHORIZATION);

    if (!Assert.has(header) || !header.startsWith(AUTHORIZATION_PREFIX)) {
      return null;
    }

    final String jwtToken = header.substring(AUTHORIZATION_PREFIX.length());

    return jwtToken;
  }
}
