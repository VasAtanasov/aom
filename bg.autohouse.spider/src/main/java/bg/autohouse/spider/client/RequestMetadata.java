package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.HttpMethod;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

public class RequestMetadata
{
    private final HttpMethod httpMethod;
    private final Collection<? extends Parameter<?>> params;
    private final RequestBody body;
    private final boolean async;
    private final URI uri;

    public RequestMetadata(RequestBuilder builder)
    {
        this.httpMethod = builder.method;
        this.params = builder.params;
        this.async = builder.async;
        this.uri = builder.uri;
        this.body = builder.body;
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

    public RequestBody body()
    {
        return body;
    }
}
