package bg.autohouse.spider.client;

import bg.autohouse.spider.api.HttpStrategy;
import bg.autohouse.spider.service.AppConfigurationService;

public class ALApiClientImpl extends AbstractApiClient {
  public static final String API_BASE_URL = AppConfigurationService.getALApiBaseUrl();

  public ALApiClientImpl(HttpStrategy httpStrategy) {
    super(httpStrategy);
  }

  @Override
  public String apiBaseUrl() {
    return API_BASE_URL;
  }
}
