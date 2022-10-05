package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.bg.api.option.OptionDTO;
import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.ResponseBodyHandler;
import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.RequestBody;
import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.client.endpoint.PostEnabled;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

public class OptionsEndpoint extends AbstractEndpoint
    implements PostEnabled<Map<String, List<OptionDTO>>>, CGEndpoint {
  public static final String TRIM_OPTIONS_FORM_URL = "Cars/getOptionsJson.action";
  private final String trimId;

  public OptionsEndpoint(ApiClient client, String trimId) {
    super(client, TRIM_OPTIONS_FORM_URL);
    this.trimId = trimId;
  }

  @Override
  public Response<Map<String, List<OptionDTO>>> POST(
      RequestBody body, QueryParameter... queryParameters) {
    var metadataOPTS =
        RequestBuilder.post(endpoint(), body).params(queryParameters).retries(5).build();
    return http()
        .call(metadataOPTS, ResponseBodyHandler.BodyHandlers.ofJson(new TypeReference<>() {}));
  }
}
