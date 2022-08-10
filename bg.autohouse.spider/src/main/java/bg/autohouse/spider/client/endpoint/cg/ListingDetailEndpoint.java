package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.ResponseBodyHandler;
import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.client.endpoint.GetEnabled;
import bg.autohouse.spider.domain.dto.cg.ListingDetailWrapperDTO;
import com.fasterxml.jackson.core.type.TypeReference;

public class ListingDetailEndpoint extends AbstractEndpoint
    implements GetEnabled<ListingDetailWrapperDTO> {
  private static final String ENDPOINT = "Cars/detailListingJson.action";

  public ListingDetailEndpoint(ApiClient client) {
    super(client, ENDPOINT);
  }

  @Override
  public Response<ListingDetailWrapperDTO> GET(QueryParameter... queryParameters) {
    var metadata = RequestBuilder.get(endpoint()).params(queryParameters).build();
    return http().call(metadata, ResponseBodyHandler.BodyHandlers.ofJson(new TypeReference<>() {}));
  }
}
