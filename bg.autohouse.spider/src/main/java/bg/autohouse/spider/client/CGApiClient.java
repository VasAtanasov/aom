package bg.autohouse.spider.client;

import bg.autohouse.spider.client.endpoint.MakerCGEndpoint;
import bg.autohouse.spider.client.endpoint.MakerCollectionCGEndpoint;

public class CGApiClient extends AbstractApiClient
{
    private static final String API_BASE_URL = "https://www.cargurus.com/Cars";

    public MakerCollectionCGEndpoint makers()
    {
        return new MakerCollectionCGEndpoint(this);
    }

    public MakerCGEndpoint maker(String id)
    {
        return new MakerCGEndpoint(this, id);
    }

    @Override
    public String apiBaseUrl()
    {
        return API_BASE_URL;
    }
}
