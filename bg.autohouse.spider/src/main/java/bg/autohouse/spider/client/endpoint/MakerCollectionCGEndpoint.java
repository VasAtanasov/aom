package bg.autohouse.spider.client.endpoint;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.ResponseBodyHandler;
import bg.autohouse.spider.client.api.AbstractEndpoint;
import bg.autohouse.spider.client.api.ApiClient;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import bg.autohouse.spider.util.json.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class MakerCollectionCGEndpoint extends AbstractEndpoint
{
    private static final String MAKER_MODEL_URL = "getCarPickerReferenceDataAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT";

    public MakerCollectionCGEndpoint(ApiClient client)
    {
        super(client, MAKER_MODEL_URL);
    }

    public List<MakerDTO> httpCollectionGet()
    {
        var metadata = RequestBuilder.get(endpoint()).build();
        ResponseBodyHandler<List<MakerDTO>> handler = in -> JsonUtil.fromJSON(in, new TypeReference<List<MakerDTO>>() {});
        Response<List<MakerDTO>> response = client().http().call(metadata, handler);
        return response.body();
//        return List.of();
    }
}
