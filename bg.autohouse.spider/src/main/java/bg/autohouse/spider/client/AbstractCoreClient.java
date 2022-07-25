package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.CoreClient;
import bg.autohouse.spider.util.json.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractCoreClient implements CoreClient
{
    private HttpExecutor httpExecutor = new JavaHttpClientStringExecutor();
    private final ObjectMapper mapper = ObjectMapperFactory.mapper();

    @Override
    public HttpExecutor http()
    {
        return httpExecutor;
    }

    @Override
    public void setHttpExecutor(HttpExecutor httpExecutor)
    {
        this.httpExecutor = httpExecutor;
    }

    @Override
    public ObjectMapper mapper()
    {
        return mapper;
    }
}
