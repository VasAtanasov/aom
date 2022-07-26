package bg.autohouse.spider.client.api;


public abstract class AbstractEndpoint
{
    protected static final String SEPARATOR = "/";

    private final ApiClient client;
    private final String url;

    public AbstractEndpoint(ApiClient client, String url)
    {
        this.client = client;
        this.url = String.join(SEPARATOR, client().apiBaseUrl(), url);
    }

    public String endpoint()
    {
        return url;
    }

    public ApiClient client()
    {
        return client;
    }

}
