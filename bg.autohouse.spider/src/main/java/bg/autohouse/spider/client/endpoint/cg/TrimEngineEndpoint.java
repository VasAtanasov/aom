package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.api.dto.engine.EngineDTO;
import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.ResponseBodyHandler;
import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.client.endpoint.GetEnabled;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class TrimEngineEndpoint extends AbstractEndpoint
    implements GetEnabled<List<EngineDTO>>, CGEndpoint {
  public static final String TRIM_ENGINE_URL =
      "Cars/getEngineList.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&trim=";

  public TrimEngineEndpoint(ApiClient client, String trimId) {
    super(client, TRIM_ENGINE_URL + trimId);
  }

  @Override
  public Response<List<EngineDTO>> GET(QueryParameter... queryParameters) {
    var metadata = RequestBuilder.get(endpoint()).params(queryParameters).retries(5).build();
    return http().call(metadata, ResponseBodyHandler.BodyHandlers.ofJson(new TypeReference<>() {}));
  }
}
