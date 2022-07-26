package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractApiClient implements ApiClient
{
    private final HttpStrategy httpStrategy;
    private final ObjectMapper mapper = mapper();

    public AbstractApiClient(HttpStrategy httpStrategy, ObjectMapper objectMapper)
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
}
