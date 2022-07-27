package bg.autohouse.spider.api;

import bg.autohouse.spider.client.HttpStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface ApiClient
{
    HttpStrategy http();
    String apiBaseUrl();
}
