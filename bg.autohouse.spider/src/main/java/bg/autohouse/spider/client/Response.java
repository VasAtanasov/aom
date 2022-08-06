package bg.autohouse.spider.client;

import java.io.Serializable;

public class Response<T> extends AbstractResponse<T> implements Serializable {
  Response(String url, int statusCode, T body) {
    super(url, statusCode, body);
  }
}
