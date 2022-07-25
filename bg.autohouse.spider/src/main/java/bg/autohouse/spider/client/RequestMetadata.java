package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.HttpMethod;
import bg.autohouse.spider.client.api.MediaType;

import java.net.URI;

public class RequestMetadata
{
    private HttpMethod httpMethod = HttpMethod.GET;
    private String mediaType = MediaType.APPLICATION_JSON_VALUE;
    private final boolean isAsyncCall = false;
    private URI uri;

    public HttpMethod method()
    {
        return httpMethod;
    }

    public String mediaType()
    {
        return mediaType;
    }

    public URI uri()
    {
        return uri;
    }

    public void setHttpMethod(HttpMethod httpMethod)
    {
        this.httpMethod = httpMethod;
    }

    public void setMediaType(String mediaType)
    {
        this.mediaType = mediaType;
    }

    public void setUri(URI uri)
    {
        this.uri = uri;
    }
}
