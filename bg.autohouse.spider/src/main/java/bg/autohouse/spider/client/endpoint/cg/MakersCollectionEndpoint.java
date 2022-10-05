package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.bg.api.maker.MakersModelsWrapper;
import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.ResponseBodyHandler;
import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.client.endpoint.GetEnabled;

public class MakersCollectionEndpoint extends AbstractEndpoint
    implements GetEnabled<MakersModelsWrapper>, CGEndpoint {
  private static final String MAKER_MODEL_URL =
      "Cars/getCarPickerReferenceDataAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT";

  public MakersCollectionEndpoint(ApiClient client) {
    super(client, MAKER_MODEL_URL);
  }

  @Override
  public Response<MakersModelsWrapper> GET(QueryParameter... queryParameters) {
    var metadata = RequestBuilder.get(endpoint()).params(queryParameters).build();
    return http()
        .call(metadata, ResponseBodyHandler.BodyHandlers.ofJson(MakersModelsWrapper.class));
  }
}
