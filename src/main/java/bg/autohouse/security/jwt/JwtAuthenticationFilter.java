package bg.autohouse.security.jwt;

import bg.autohouse.data.models.User;
import bg.autohouse.security.SecurityConstants;
import bg.autohouse.security.SecurityUtils;
import bg.autohouse.service.services.AsyncUserLogger;
import bg.autohouse.web.models.request.UserLoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final AsyncUserLogger userLogger;

  public JwtAuthenticationFilter(
      AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider,
      AsyncUserLogger userLogger) {
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.userLogger = userLogger;
    this.setFilterProcessesUrl("/api/auth/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {

    try {
      UserLoginRequest credentials =
          new ObjectMapper().readValue(req.getInputStream(), UserLoginRequest.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              credentials.getUsername(), credentials.getPassword(), new ArrayList<>()));

    } catch (IOException e) {
      log.error("Could not set user authentication in security context", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
      throws IOException, ServletException {

    User user = SecurityUtils.extractFrom(auth);

    JwtTokenCreateRequest createRequest =
        new JwtTokenCreateRequest(JwtTokenType.VERIFICATION, user);
    String token = tokenProvider.createJwt(createRequest);
    userLogger.logUserLogin(user.getId());

    res.getWriter()
        .append(SecurityConstants.HEADER_STRING + ": " + SecurityConstants.TOKEN_PREFIX + token);
    res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    res.addHeader("UserID", user.getId());
  }
}
