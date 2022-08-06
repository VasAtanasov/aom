package bg.autohouse.spider.client;

import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.HttpStrategy;
import bg.autohouse.spider.client.endpoint.cg.MakerEndpoint;
import bg.autohouse.spider.client.endpoint.cg.MakersCollectionEndpoint;
import bg.autohouse.spider.client.endpoint.cg.ModelEndpoint;
import bg.autohouse.spider.client.endpoint.cg.OptionsEndpoint;
import bg.autohouse.spider.client.endpoint.cg.TrimEngineEndpoint;
import bg.autohouse.spider.client.endpoint.cg.TrimTransmissionEndpoint;

abstract class AbstractApiClient implements ApiClient {
  private final HttpStrategy httpStrategy;

  public AbstractApiClient(HttpStrategy httpStrategy) {
    this.httpStrategy = httpStrategy;
  }

  public HttpStrategy http() {
    return httpStrategy;
  }
}
