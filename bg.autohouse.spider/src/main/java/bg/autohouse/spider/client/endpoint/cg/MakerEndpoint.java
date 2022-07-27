package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.ResponseBodyHandler;
import bg.autohouse.spider.client.api.ApiClient;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;

public class MakerEndpoint extends AbstractEndpoint
{
    private static final String MODELS_FOR_MAKER_URL =
            "getSelectedMakerModelCarsAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&maker=";

    public MakerEndpoint(ApiClient client, String makerId)
    {
        super(client, MODELS_FOR_MAKER_URL + makerId);
    }

    public ModelsCarsWrapper models()
    {
        var metadata = RequestBuilder.get(endpoint()).build();
        Response<ModelsCarsWrapper> response = http().call(metadata, ResponseBodyHandler.BodyHandlers.ofJson(ModelsCarsWrapper.class));
        return response.body();
    }
}
