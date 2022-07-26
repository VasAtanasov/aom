package bg.autohouse.spider.client;

import bg.autohouse.spider.client.exception.HttpClientException;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public class JavaHttpClientStrategy extends AbstractHttpStrategy
{
    private final HttpClient http = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    @Override
    protected RawResponse call(RequestMetadata metadata)
    {
        HttpRequest.Builder builder = HttpRequest.newBuilder(metadata.uri())
                .timeout(Duration.ofMillis(metadata.connectTimeout()));

        switch (metadata.method())
        {
            case GET:
                builder.GET();
                break;
            case POST:
                builder.header(metadata.contentType().name(), metadata.contentType().value());
                builder.POST(HttpRequest.BodyPublishers.ofInputStream(metadata::body));
                break;
            default:
                throw new UnsupportedOperationException("Unsupported method: " + metadata.method());
        }

        HttpRequest request = builder.build();
        HttpResponse<InputStream> response;
        HttpResponse.BodyHandler<InputStream> bodyHandler = HttpResponse.BodyHandlers.ofInputStream();

        for (int i = 0; i < metadata.retries() + 1; i++)
        {
            try
            {
                response = http.send(request, bodyHandler);
                if (response.statusCode() >= 200 && response.statusCode() < 300)
                {
                    InputStream body = response.body();
                    return new RawResponse(metadata.uri().toString(), response.statusCode(), body);
                }
            }
            catch (Exception e)
            {
                throw new HttpClientException(String.format("Failed to send http %s to %s", metadata.method(), metadata.uri()), e);
            }
        }

        throw new HttpClientException(String.format("Could not perform http %s call to %s", metadata.method(), metadata.uri()));
    }
}
