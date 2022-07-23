package bg.autohouse.spider.client;

import java.io.Serializable;

public class Response<T> implements Serializable {
  private final String url;
  private final int statusCode;
  private final T body;

  Response(String url, int statusCode, T body) {
    this.url = url;
    this.statusCode = statusCode;
    this.body = body;
  }

  public T body() {
    return this.body;
  }

  public String url() {
    return url;
  }

  public int statusCode() {
    return statusCode;
  }
}
