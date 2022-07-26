package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.HttpMethod;

import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;

public class RequestMetadata
{
    private final HttpMethod httpMethod;
    private final URI uri;
    private final Collection<? extends Parameter<?>> params;
    private final RequestBody body;
    private final boolean async;
    private final int connectTimeout;
    private final int retries;

    public RequestMetadata(RequestBuilder builder)
    {
        this.httpMethod = builder.method;
        this.params = builder.params;
        this.async = builder.async;
        this.uri = builder.uri;
        this.body = builder.body;
        this.connectTimeout = builder.connectTimeout;
        this.retries = builder.retries;
    }

    public HttpMethod method()
    {
        return httpMethod;
    }

    public URI uri()
    {
        return uri;
    }

    public boolean isAsync()
    {
        return async;
    }

    public Collection<? extends Parameter<?>> params()
    {
        return Collections.unmodifiableCollection(params);
    }

    public InputStream body()
    {
        return body.getBody();
    }

    public Header contentType()
    {
        return body.getContentType();
    }

    public int connectTimeout()
    {
        return connectTimeout;
    }

    public int retries()
    {
        return retries;
    }
}
