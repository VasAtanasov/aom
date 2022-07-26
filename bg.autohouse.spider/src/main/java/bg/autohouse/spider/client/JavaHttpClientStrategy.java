package bg.autohouse.spider.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

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
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(metadata.uri());
        switch (metadata.method())
        {
            case GET:
                builder.GET();
                break;
            case POST:
                builder.header("Content-Type", metadata.body().getContentType().value());
                builder.POST(HttpRequest.BodyPublishers.ofInputStream(() -> metadata.body().getBody()));
                break;
            default:
                throw new UnsupportedOperationException("Unsupported method: " + metadata.method());
        }

        HttpRequest request = builder.build();

        try
        {
            HttpResponse.BodyHandler<InputStream> bodyHandler = HttpResponse.BodyHandlers.ofInputStream();
//            HttpResponse.BodyHandler<InputStream> bodyHandler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<InputStream> response = http.send(request, bodyHandler);
            InputStream body = response.body();
            return new RawResponse(metadata.uri().toString(), response.statusCode(), body);
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
