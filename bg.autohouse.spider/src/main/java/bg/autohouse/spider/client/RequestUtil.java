package bg.autohouse.spider.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestUtil
{
    private static final Logger LOG = LoggerFactory.getLogger("CoreClient");


    private static <T> HttpResponse<T> execute(HttpRequest request, HttpResponse.BodyHandler<T> handler)
    {
        HttpClient httpClient = HttpClient.newHttpClient();

        try
        {
            return httpClient.send(request, handler);
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void logRequest(URI request)
    {
        LOG.info("Request URL: {}", request);
    }


    public static <T> HttpResponse<T> get(URI uri, HttpResponse.BodyHandler<T> handler)
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        logRequest(uri);

        return execute(request, handler);
    }

    public static HttpResponse<String> getAsString(URI uri)
    {
        return get(uri, HttpResponse.BodyHandlers.ofString());
    }

    public static <T> T fromJSON(ObjectMapper objectMapper, TypeReference<T> type, String jsonPacket)
    {
        if (jsonPacket == null)
        {
            return null;
        }

        T data = null;

        try
        {
            data = objectMapper.readValue(jsonPacket, type);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return data;
    }
}
