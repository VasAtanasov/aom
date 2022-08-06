package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.ResponseBodyHandler;
import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.client.endpoint.GetEnabled;
import bg.autohouse.spider.domain.dto.cg.TransmissionWrapper;

public class TrimTransmissionEndpoint extends AbstractEndpoint
    implements GetEnabled<TransmissionWrapper>, CGEndpoint {
  public static final String TRIM_TRANSMISSION_URL =
      "Cars/getTransmissionListTrimFirstJson.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&trim=";

  public TrimTransmissionEndpoint(ApiClient client, String trimId) {
    super(client, TRIM_TRANSMISSION_URL + trimId);
  }

  @Override
  public Response<TransmissionWrapper> GET(QueryParameter... queryParameters) {
    var metadata = RequestBuilder.get(endpoint()).params(queryParameters).build();
    return http()
        .call(metadata, ResponseBodyHandler.BodyHandlers.ofJson(TransmissionWrapper.class));
  }
}
