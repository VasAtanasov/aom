package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.ResponseBodyHandler;
import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.client.endpoint.GetEnabled;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;

public class MakerEndpoint extends AbstractEndpoint
    implements GetEnabled<ModelsCarsWrapper>, CGEndpoint {
  private static final String MODELS_FOR_MAKER_URL =
      "Cars/getSelectedMakerModelCarsAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&maker=";

  public MakerEndpoint(ApiClient client, String makerId) {
    super(client, MODELS_FOR_MAKER_URL + makerId);
  }

  @Override
  public Response<ModelsCarsWrapper> GET(QueryParameter... queryParameters) {
    var metadata = RequestBuilder.get(endpoint()).params(queryParameters).build();
    return http().call(metadata, ResponseBodyHandler.BodyHandlers.ofJson(ModelsCarsWrapper.class));
  }
}
