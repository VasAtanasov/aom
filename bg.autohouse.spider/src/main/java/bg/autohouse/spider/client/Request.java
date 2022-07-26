package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.HttpMethod;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collection;

public class Request
{

    private final HttpMethod method;
    private final Collection<? extends Parameter<?>> params;
    private final URI url;
    private final RequestBody body;
    private final Charset charset;
    private final int socksTimeout;
    private final int connectTimeout;

    Request(RequestBuilder builder)
    {
        this.method = builder.method;
        this.charset = builder.charset;
        this.body = builder.body;
        this.socksTimeout = builder.socksTimeout;
        this.connectTimeout = builder.connectTimeout;
        this.url = builder.uri;
        this.params = builder.params;
    }

    public HttpMethod method()
    {
        return method;
    }

    public Collection<? extends Parameter<?>> params()
    {
        return params;
    }

    public URI url()
    {
        return url;
    }

    public RequestBody body()
    {
        return body;
    }

    public Charset charset()
    {
        return charset;
    }

    public int socksTimeout()
    {
        return socksTimeout;
    }

    public int connectTimeout()
    {
        return connectTimeout;
    }
}
