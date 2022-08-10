package bg.autohouse.spider.client;

import bg.autohouse.spider.api.HttpStrategy;
import bg.autohouse.spider.client.endpoint.cg.*;
import bg.autohouse.spider.service.AppConfigurationService;

public class CGApiClientImpl extends AbstractApiClient {
  public static final String API_BASE_URL = AppConfigurationService.getCGApiBaseUrl();

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

  public SearchResultEndpoint listingsSearch() {
    return new SearchResultEndpoint(this);
  }

  public ListingDetailEndpoint listingDetail() {
    return new ListingDetailEndpoint(this);
  }

  public String apiBaseUrl() {
    return API_BASE_URL;
  }
}
