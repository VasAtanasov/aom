package bg.autohouse.spider.client;

import bg.autohouse.spider.client.endpoint.cg.*;
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

    public ModelEndpoint modelCars(String id)
    {
        return new ModelEndpoint(this, id);
    }

    public TrimTransmissionEndpoint trimTransmissions(String trimId)
    {
        return new TrimTransmissionEndpoint(this, trimId);
    }

    public TrimEngineEndpoint trimEngines(String trimId)
    {
        return new TrimEngineEndpoint(this, trimId);
    }

    @Override
    public String apiBaseUrl()
    {
        return API_BASE_URL;
    }
}
