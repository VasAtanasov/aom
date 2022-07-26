package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.ResponseBodyHandler;
import bg.autohouse.spider.client.api.AbstractEndpoint;
import bg.autohouse.spider.client.api.ApiClient;
import bg.autohouse.spider.domain.dto.cg.ModelCarsDTO;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;
import bg.autohouse.spider.util.json.JsonUtil;

import java.util.List;

public class MakerEndpoint extends AbstractEndpoint
{
    private static final String MODELS_FOR_MAKER_URL = "getSelectedMakerModelCarsAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&maker=";
    private static final ResponseBodyHandler<ModelsCarsWrapper> HANDLER = in -> JsonUtil.fromJSON(in, ModelsCarsWrapper.class);

    public MakerEndpoint(ApiClient client, String makerId)
    {
        super(client, String.join(MODELS_FOR_MAKER_URL, SEPARATOR, makerId));
    }

    public List<ModelCarsDTO> models()
    {
        var metadata = RequestBuilder.get(endpoint()).build();
        Response<ModelsCarsWrapper> response = http().call(metadata, HANDLER);
        return response.body().getModels();
    }
}
