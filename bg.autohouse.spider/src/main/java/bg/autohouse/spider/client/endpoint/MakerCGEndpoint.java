package bg.autohouse.spider.client.endpoint;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.ResponseBodyHandler;
import bg.autohouse.spider.client.api.ApiClient;
import bg.autohouse.spider.client.api.AbstractEndpoint;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import bg.autohouse.spider.util.json.JsonUtil;

public class MakerCGEndpoint extends AbstractEndpoint
{
    private static final String MODELS_FOR_MAKER_URL = "getSelectedMakerModelCarsAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&maker=";

    public MakerCGEndpoint(ApiClient client, String makerId)
    {
        super(client, String.join(MODELS_FOR_MAKER_URL, SEPARATOR, makerId));
    }

    public MakerDTO getMaker()
    {
        var metadata = RequestBuilder.get(endpoint()).build();
        ResponseBodyHandler<MakerDTO> handler = in -> JsonUtil.fromJSON(in, MakerDTO.class);
        Response<MakerDTO> response = client().http().call(metadata, handler);
        return response.body();
    }
}
