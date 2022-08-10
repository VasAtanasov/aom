package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.ResponseBodyHandler;
import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.client.endpoint.GetEnabled;
import bg.autohouse.spider.domain.dto.cg.ListingDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class SearchResultEndpoint extends AbstractEndpoint
    implements GetEnabled<List<ListingDTO>>, CGEndpoint {
  private static final String ENDPOINT = "Cars/searchResults.action";

  public SearchResultEndpoint(ApiClient client) {
    super(client, ENDPOINT);
  }

  @Override
  public Response<List<ListingDTO>> GET(QueryParameter... queryParameters) {
    var metadata = RequestBuilder.get(endpoint()).params(queryParameters).build();
    return http().call(metadata, ResponseBodyHandler.BodyHandlers.ofJson(new TypeReference<>() {}));
  }
}
