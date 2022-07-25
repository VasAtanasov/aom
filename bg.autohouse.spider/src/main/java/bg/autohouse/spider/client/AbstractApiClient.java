package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.CoreClient;
import bg.autohouse.spider.util.json.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractCoreClient implements CoreClient
{
    private HttpStrategy httpStrategy = new JavaHttpClientStrategy();
    private final ObjectMapper mapper = ObjectMapperFactory.mapper();

    public AbstractCoreClient()
    {
    }

    public AbstractCoreClient(HttpStrategy httpStrategy)
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
