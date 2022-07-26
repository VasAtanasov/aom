package bg.autohouse.spider.client;

import java.io.Serializable;

public class Response<T> extends AbstractResponse implements Serializable {
  private final T body;

  Response(String url, int statusCode, T body) {
    super(url, statusCode);
    this.body = body;
  }

  public T body() {
    return this.body;
  }
}
