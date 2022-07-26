package bg.autohouse.spider.client;

import bg.autohouse.spider.client.endpoint.cg.MakerEndpoint;
import bg.autohouse.spider.client.endpoint.cg.MakersCollectionEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CGApiClient extends AbstractApiClient
{
    private static final String API_BASE_URL = "https://www.cargurus.com/Cars";

    public CGApiClient(HttpStrategy httpStrategy, ObjectMapper objectMapper)
    {
        super(httpStrategy, objectMapper);
    }

    public MakersCollectionEndpoint makers()
    {
        return new MakersCollectionEndpoint(this);
    }

    public MakerEndpoint maker(String id)
    {
        return new MakerEndpoint(this, id);
    }

    @Override
    public String apiBaseUrl()
    {
        return API_BASE_URL;
    }
}
