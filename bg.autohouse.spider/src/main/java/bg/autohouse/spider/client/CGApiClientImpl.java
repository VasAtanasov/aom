package bg.autohouse.spider.client;

import bg.autohouse.spider.api.HttpStrategy;
import bg.autohouse.spider.client.endpoint.cg.MakerEndpoint;
import bg.autohouse.spider.client.endpoint.cg.MakersCollectionEndpoint;
import bg.autohouse.spider.client.endpoint.cg.ModelEndpoint;
import bg.autohouse.spider.client.endpoint.cg.OptionsEndpoint;
import bg.autohouse.spider.client.endpoint.cg.TrimEngineEndpoint;
import bg.autohouse.spider.client.endpoint.cg.TrimTransmissionEndpoint;

public class CGApiClientImpl extends AbstractApiClient {
  private static final String API_BASE_URL = "https://www.cargurus.com";

  public CGApiClientImpl(HttpStrategy httpStrategy) {
    super(httpStrategy);
  }

  public MakersCollectionEndpoint makers() {
    return new MakersCollectionEndpoint(this);
  }

  public MakerEndpoint maker(String id) {
    return new MakerEndpoint(this, id);
  }

  public ModelEndpoint modelCars(String id) {
    return new ModelEndpoint(this, id);
  }

  public TrimTransmissionEndpoint trimTransmissions(String trimId) {
    return new TrimTransmissionEndpoint(this, trimId);
  }

  public TrimEngineEndpoint trimEngines(String trimId) {
    return new TrimEngineEndpoint(this, trimId);
  }

  public OptionsEndpoint trimOptions(String trimId) {
    return new OptionsEndpoint(this, trimId);
  }

  public String apiBaseUrl() {
    return API_BASE_URL;
  }
}
