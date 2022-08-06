package bg.autohouse.spider.client;

import bg.autohouse.spider.api.HttpMethod;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class RequestBuilder {
  HttpMethod method = HttpMethod.GET;
  URI uri;
  Collection<QueryParameter> params = List.of();
  Charset charset = StandardCharsets.UTF_8;
  RequestBody body;
  boolean async = false;
  int connectTimeout = 3000;

  RequestBuilder() {}

  private static RequestBuilder builder() {
    return new RequestBuilder();
  }

  public static RequestBuilder get(String url) {
    return builder(HttpMethod.GET, url);
  }

  public static RequestBuilder post(String url, RequestBody body) {
    return builder(HttpMethod.POST, url).body(body);
  }

  private static RequestBuilder builder(HttpMethod method, String url) {
    return builder().method(method).uri(URI.create(url));
  }

  public RequestBuilder method(HttpMethod method) {
    this.method = method;
    return this;
  }

  public RequestBuilder uri(URI uri) {
    this.uri = uri;
    return this;
  }

  public RequestBuilder params(Collection<QueryParameter> params) {
    this.params = params;
    return this;
  }

  public RequestBuilder params(QueryParameter[] params) {
    if (params.length == 0) return this;
    this.params = Arrays.asList(params);
    return this;
  }

  public RequestBuilder charset(Charset charset) {
    this.charset = charset;
    return this;
  }

  public RequestBuilder body(RequestBody body) {
    this.body = body;
    return this;
  }

  public RequestBuilder async(boolean async) {
    this.async = async;
    return this;
  }

  public RequestBuilder connectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
    return this;
  }

  public RequestMetadata build() {
    return new RequestMetadata(this);
  }
}
