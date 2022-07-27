package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.ResponseBodyHandler;
import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.domain.dto.cg.EngineDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class TrimEngineEndpoint extends AbstractEndpoint
{
    public static final String TRIM_ENGINE_URL =
            "getEngineList.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&trim=";

    public TrimEngineEndpoint(ApiClient client, String trimId)
    {
        super(client, TRIM_ENGINE_URL + trimId);
    }

    public List<EngineDTO> engines()
    {
        var metadata = RequestBuilder.get(endpoint()).build();
        ResponseBodyHandler<List<EngineDTO>> handler = ResponseBodyHandler.BodyHandlers.ofJson(new TypeReference<>(){});
        Response<List<EngineDTO>> response = http().call(metadata, handler);
        return response.body();
    }
}
