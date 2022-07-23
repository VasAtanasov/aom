package bg.autohouse.spider.client;

import java.io.Serializable;

public abstract class RequestBody<T> implements Serializable {

  private final T body;
  private final String contentType;

  protected RequestBody(T body, String contentType) {
    this.body = body;
    this.contentType = contentType;
  }

  public T body() {
    return this.body;
  }

  public String contentType() {
    return this.contentType;
  }
}
