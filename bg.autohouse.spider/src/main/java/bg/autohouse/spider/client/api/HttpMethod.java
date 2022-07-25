package bg.autohouse.spider.client.api;

import java.util.HashMap;
import java.util.Map;

public enum HttpMethod
{
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    private static final Map<String, HttpMethod> mappings = new HashMap<>();

    public static HttpMethod resolve(String method)
    {
        return method != null ? mappings.get(method) : null;
    }

    public boolean matches(String method)
    {
        return this.name().equals(method);
    }

    static
    {
        HttpMethod[] httpMethodsArray = values();
        for (HttpMethod httpMethod : httpMethodsArray)
        {
            mappings.put(httpMethod.name(), httpMethod);
        }
    }
}
