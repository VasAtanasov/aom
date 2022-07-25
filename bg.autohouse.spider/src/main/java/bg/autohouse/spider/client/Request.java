package bg.autohouse.spider.client;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class Request
{

    private final String method;
    private final Collection<? extends Parameter<?>> params;
    private final URL url;
    private final RequestBody<?> body;
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
        this.url = builder.url;
        this.params = builder.params;
    }

    public String method()
    {
        return method;
    }

    public Collection<? extends Parameter<?>> params()
    {
        return params;
    }

    public URL url()
    {
        return url;
    }

    public RequestBody<?> body()
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
