package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.ResponseBodyHandler;
import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;

public class MakersCollectionEndpoint extends AbstractEndpoint
{
    private static final String MAKER_MODEL_URL =
            "getCarPickerReferenceDataAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT";

    public MakersCollectionEndpoint(ApiClient client)
    {
        super(client, MAKER_MODEL_URL);
    }

    public MakersModelsWrapper makers()
    {
        var metadata = RequestBuilder.get(endpoint()).build();
        ResponseBodyHandler<MakersModelsWrapper> handler = ResponseBodyHandler.BodyHandlers.ofJson(MakersModelsWrapper.class);
        Response<MakersModelsWrapper> response = http().call(metadata, handler);
        return response.body();
    }
}
