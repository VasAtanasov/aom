package bg.autohouse.spider.client;

import bg.autohouse.spider.api.HttpMethod;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;

public class RequestMetadata {
  private final HttpMethod httpMethod;
  private final URI uri;
  private final Collection<? extends QueryParameter> params;
  private final RequestBody body;
  private final boolean async;
  private final int connectTimeout;

  public RequestMetadata(RequestBuilder builder) {
    this.httpMethod = builder.method;
    this.params = builder.params;
    this.async = builder.async;
    this.uri = builder.uri;
    this.body = builder.body;
    this.connectTimeout = builder.connectTimeout;
  }

  public HttpMethod method() {
    return httpMethod;
  }

  public URI uri() {
    return uri;
  }

  public boolean isAsync() {
    return async;
  }

  public Collection<? extends QueryParameter> params() {
    return Collections.unmodifiableCollection(params);
  }

  public InputStream body() {
    return body.getBody();
  }

  public Header contentType() {
    return body.getContentType();
  }

  public int connectTimeout() {
    return connectTimeout;
  }
}
