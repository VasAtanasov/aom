package bg.autohouse.spider.api;

import bg.autohouse.spider.client.RequestMetadata;
import bg.autohouse.spider.client.Response;

public interface HttpStrategy
{
    <T> Response<T> call(RequestMetadata metadata, ResponseBodyHandler<T> handler);
}
