package bg.autohouse.spider.client;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class JavaHttpClientStrategy
{
    private final HttpClient http = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    protected  <T> Response<T> call(RequestMetadata metadata)
    {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.header("Content-Type", metadata.mediaType());
        builder.uri(metadata.uri());
        switch (metadata.method())
        {
            case GET:
                builder.GET();
                break;
            case POST:
                //                builder.POST()
                break;
            default:
                throw new UnsupportedOperationException("Unsupported method: " + metadata.method());
        }

        HttpRequest request = builder.build();

        try
        {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            response.body();
            //            Response<String> resp = new Response<>();
            //            res

        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }


        return null;
    }
}
