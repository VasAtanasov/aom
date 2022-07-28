package bg.autohouse.spider.client.endpoint;

import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.Response;

public interface GetEnabled<T>
{
    Response<T> GET(QueryParameter... queryParameters);
}
