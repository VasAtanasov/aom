package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.ApiClient;
import bg.autohouse.spider.util.json.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractApiClient implements ApiClient
{
    private HttpStrategy httpStrategy = new JavaHttpClientStrategy();
    private final ObjectMapper mapper = ObjectMapperFactory.mapper();

    public AbstractApiClient()
    {
    }

    public AbstractApiClient(HttpStrategy httpStrategy)
    {
        this.httpStrategy = httpStrategy;
    }

    @Override
    public ObjectMapper mapper()
    {
        return mapper;
    }

    public HttpStrategy http()
    {
        return httpStrategy;
    }

    public void setHttpStrategy(HttpStrategy httpStrategy)
    {
        this.httpStrategy = httpStrategy;
    }

}
