package bg.autohouse.spider.client.endpoint;

import bg.autohouse.spider.client.HttpStrategy;
import bg.autohouse.spider.client.api.ApiClient;

public class AbstractEndpoint
{
    protected static final String SEPARATOR = "/";

    private final ApiClient client;
    private final String endpoint;

    public AbstractEndpoint(ApiClient client, String endpoint)
    {
        this.client = client;
        this.endpoint = String.join(SEPARATOR, client().apiBaseUrl(), endpoint);
    }

    public String endpoint()
    {
        return endpoint;
    }

    public ApiClient client()
    {
        return client;
    }

    protected HttpStrategy http()
    {
        return this.client.http();
    }

}
