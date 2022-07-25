package bg.autohouse.spider.client.api;

import bg.autohouse.spider.client.HttpExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface CoreClient
{
    HttpExecutor http();
    void setHttpExecutor(HttpExecutor httpExecutor);
    ObjectMapper mapper();
    String apiBaseUrl();
}
