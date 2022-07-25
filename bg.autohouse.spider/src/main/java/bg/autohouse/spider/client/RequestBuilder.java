package bg.autohouse.spider.client;

import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

public final class RequestBuilder
{
    String method = "GET";
    URL url;
    Collection<? extends Parameter<?>> params = List.of();
    Charset charset = StandardCharsets.UTF_8;
    RequestBody<?> body;
    int socksTimeout = 5000;
    int connectTimeout = 3000;

    public static RequestBuilder newBuilder()
    {
        return new RequestBuilder();
    }

    RequestBuilder() {}

    RequestBuilder(Request request) {
        this.method = request.method();
        this.charset = request.charset();
        this.body = request.body();
        this.socksTimeout = request.socksTimeout();
        this.connectTimeout = request.connectTimeout();
        this.url = request.url();
        this.params = request.params();
    }

    public RequestBuilder method(String method)
    {
        this.method = method;
        return this;
    }

    public RequestBuilder url(URL url)
    {
        this.url = url;
        return this;
    }

    public RequestBuilder params(Collection<? extends Parameter<?>> params)
    {
        this.params = params;
        return this;
    }

    public RequestBuilder charset(Charset charset)
    {
        this.charset = charset;
        return this;
    }

    public RequestBuilder body(RequestBody<?> body)
    {
        this.body = body;
        return this;
    }

    public RequestBuilder socksTimeout(int socksTimeout)
    {
        this.socksTimeout = socksTimeout;
        return this;
    }

    public RequestBuilder connectTimeout(int connectTimeout)
    {
        this.connectTimeout = connectTimeout;
        return this;
    }

    Request build()
    {
        return new Request(this);
    }
}
