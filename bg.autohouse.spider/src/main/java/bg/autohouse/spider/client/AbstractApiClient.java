package bg.autohouse.spider.client;

import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.HttpStrategy;

abstract class AbstractApiClient implements ApiClient {
  private final HttpStrategy httpStrategy;

  public AbstractApiClient(HttpStrategy httpStrategy) {
    this.httpStrategy = httpStrategy;
  }

  public HttpStrategy http() {
    return httpStrategy;
  }
}
