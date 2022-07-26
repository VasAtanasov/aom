package bg.autohouse.spider.client;

public abstract class AbstractHttpStrategy implements HttpStrategy
{
    protected abstract RawResponse call(RequestMetadata metadata);

    public <T> Response<T> call(final RequestMetadata metadata, ResponseBodyHandler<T> handler)
    {
        RawResponse response = call(metadata);
        T result = handler.handle(response.body());
        return new Response<>(response.url(), response.statusCode(), result);
    }
}
