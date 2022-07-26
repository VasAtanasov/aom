package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.ApiClient;

abstract class AbstractApiClient implements ApiClient
{
    private final HttpStrategy httpStrategy;

    public AbstractApiClient(HttpStrategy httpStrategy)
    {
        this.httpStrategy = httpStrategy;
    }

    public HttpStrategy http()
    {
        return httpStrategy;
    }
}
