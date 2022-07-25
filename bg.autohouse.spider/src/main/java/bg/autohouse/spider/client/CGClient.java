package bg.autohouse.spider.client;

import bg.autohouse.spider.client.endpoint.MakerCGEndpoint;
import bg.autohouse.spider.client.endpoint.MakerCollectionCGEndpoint;

import static bg.autohouse.spider.constants.Constant.MAKER_MODEL_URL;
import static bg.autohouse.spider.constants.Constant.MODELS_FOR_MAKER_URL;

public class CGClient extends AbstractCoreClient
{
    private final String API_BASE_URL = "https://www.cargurus.com/Cars";

    public MakerCollectionCGEndpoint makers()
    {

        String endpoint = API_BASE_URL + "/" + MAKER_MODEL_URL;
        return new MakerCollectionCGEndpoint(this, endpoint);
    }

    public MakerCGEndpoint maker(String id)
    {
        String endpoint = API_BASE_URL + "/" + MODELS_FOR_MAKER_URL;
        return new MakerCGEndpoint(this, endpoint, id);
    }

    @Override
    public String apiBaseUrl()
    {
        return API_BASE_URL;
    }
}
