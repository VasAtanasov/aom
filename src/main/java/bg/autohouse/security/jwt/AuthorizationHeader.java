package bg.autohouse.security.jwt;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationHeader {

  private static final String BEARER_TOKEN_WORD = "bearer";
  private static final String HEADER_STRING = "Authorization";

  private HttpServletRequest request;

  public AuthorizationHeader(HttpServletRequest request) {
    this.request = request;
  }

  public String getBearerToken() {
    return this.getHeader().substring(BEARER_TOKEN_WORD.length() + 1);
  }

  public boolean hasBearerToken() {
    return !this.isNull() && getHeader().toLowerCase().startsWith(BEARER_TOKEN_WORD);
  }

  public boolean doesNotHaveBearerToken() {
    return !this.hasBearerToken();
  }

  private boolean isNull() {
    return this.getHeader() == null || this.getHeader().length() == 0;
  }

  private String getHeader() {
    return this.request.getHeader(HEADER_STRING);
  }
}
