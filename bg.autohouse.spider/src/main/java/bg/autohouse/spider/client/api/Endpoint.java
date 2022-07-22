package bg.autohouse.spider.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class Endpoint
{
    private final CoreClient client;
    private final String url;

    public Endpoint(CoreClient client, String url)
    {
        this.client = client;
        this.url = url;
    }

    public String getEndpoint()
    {
        return url;
    }

    public CoreClient getClient()
    {
        return client;
    }
}
