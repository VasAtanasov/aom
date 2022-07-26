package bg.autohouse.spider.client;

public interface HttpStrategy
{
    <T> Response<T> call(RequestMetadata metadata, ResponseBodyHandler<T> handler);
}
