package bg.autohouse.security.jwt;

import bg.autohouse.errors.NoSuchUserException;
import bg.autohouse.service.services.UserService;
import bg.autohouse.web.enums.RestMessage;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
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

    if (authHeader.hasBearerToken()) {

      try {
        if (!jwtService.isJwtTokenValid(token) || jwtService.isBlackListed(token)) {
          throw new BadCredentialsException(RestMessage.INVALID_TOKEN.name());
        }

        String userId = jwtService.getUserIdFromJWT(token);
        final UserDetails userDetails;

        try {
          userDetails = userService.loadUserById(userId);
        } catch (final NoSuchUserException notFound) {
          throw new BadCredentialsException(RestMessage.BAD_CREDENTIALS.name());
        }

        if (!userDetails.isAccountNonLocked()) {
          throw new LockedException(RestMessage.USER_ACCOUNT_LOCKED.name());
        }

        if (!userDetails.isEnabled()) {
          throw new DisabledException(RestMessage.USER_ACCOUNT_DISABLED.name());
        }

        if (!userDetails.isAccountNonExpired()) {
          throw new AccountExpiredException(RestMessage.USER_ACCOUNT_EXPIRED.name());
        }

        if (!userDetails.isCredentialsNonExpired()) {
          throw new CredentialsExpiredException(RestMessage.USER_CREDENTIALS_EXPIRED.name());
        }

        log.info("Successful JWT authentication for username={}", userDetails.getUsername());
        log.debug("User ID: {}", userId);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("Finished pushing authentication object");
      } catch (Exception e) {
        log.error("Could not set user authentication in security context");
        SecurityContextHolder.clearContext();
      }

      try {
        filterChain.doFilter(request, response);
      } finally {
        SecurityContextHolder.clearContext();
      }

    } else {
      SecurityContextHolder.clearContext();
      filterChain.doFilter(request, response);
    }
  }
}
