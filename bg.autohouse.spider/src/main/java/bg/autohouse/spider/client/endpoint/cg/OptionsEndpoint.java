package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.api.MediaType;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class OptionsEndpoint extends AbstractEndpoint
{
    private static final Logger LOG = LoggerFactory.getLogger("OptionsEndpoint");
    public static final String TRIM_OPTIONS_FORM_URL = "getOptionsJson.action";
    private final String trimId;

    public OptionsEndpoint(ApiClient client, String trimId)
    {
        super(client, TRIM_OPTIONS_FORM_URL);
        this.trimId = trimId;
    }

    public String options()
    {
        Map<Object, Object> form = new HashMap<>();
        form.put("trim", this.trimId);

        HttpClient http = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        var metadata = RequestBuilder.get(endpoint()).build();
        var formData = ofFormData(form);

        HttpRequest request = HttpRequest.newBuilder(metadata.uri())
                .timeout(Duration.ofMillis(metadata.connectTimeout()))
                .setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(formData)
                .build();

        try
        {
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data)
    {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet())
        {
            if (builder.length() > 0)
            {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        String form = builder.toString();
        LOG.info("Form: {}", form);
        return HttpRequest.BodyPublishers.ofString(form);
    }


}
