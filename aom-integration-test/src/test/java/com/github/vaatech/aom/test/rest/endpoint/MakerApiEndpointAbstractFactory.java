package com.github.vaatech.aom.test.rest.endpoint;

import com.github.vaatech.aom.Constants;
import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.test.rest.ApiEndpoint;
import com.github.vaatech.aom.test.rest.Parameters;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static com.github.vaatech.aom.test.rest.endpoint.MakerApiEndpointAbstractFactory.Endpoint.MAKER_ENDPOINT;

@Component
public class MakerApiEndpointAbstractFactory {

  public interface Endpoint {
    String MAKER_ENDPOINT = Constants.API_ENDPOINT + "/makers";
  }

  public ApiEndpoint.ApiEndpointBuilder createMakerApiEndpointBuilder(MakerDTO makerDTO) {
    return ApiEndpoint.builder().url(MAKER_ENDPOINT).httpMethod(HttpMethod.POST).body(makerDTO);
  }

  public ApiEndpoint.ApiEndpointBuilder fetchMakersPageApiEndpointBuilder(int page, int size) {
    var params = Parameters.with("page", String.valueOf(page)).and("size", String.valueOf(size));
    return ApiEndpoint.builder()
        .url(MAKER_ENDPOINT)
        .params(params.map())
        .httpMethod(HttpMethod.GET);
  }
}
