package bg.autohouse.spider.client.api;

import bg.autohouse.spider.client.HttpStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface CoreClient
{
    HttpStrategy http();
    void setHttpStrategy(HttpStrategy httpStrategy);
    ObjectMapper mapper();
    String apiBaseUrl();
}
