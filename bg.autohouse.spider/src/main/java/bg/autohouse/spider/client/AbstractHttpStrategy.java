package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

abstract class AbstractHttpStrategy implements HttpStrategy
{
    protected abstract RawResponse call(RequestMetadata metadata);

    private static final Logger LOG = LoggerFactory.getLogger("HttpStrategy");

    public <T> Response<T> call(final RequestMetadata metadata, ResponseBodyHandler<T> handler)
    {
        if (metadata.retries() < 0)
        {
            throw new IllegalArgumentException("Retries must be 0 or greater");
        }

        try (RawResponse response = call(metadata))
        {
            logRequest(metadata.method(), metadata.uri());
            T result = handler.handle(response.body());
            return new Response<>(response.url(), response.statusCode(), result);
        }
    }

    protected static void logRequest(HttpMethod method, URI request)
    {
        LOG.info("Http {} Request to URL: {}", method, request);
    }
}
