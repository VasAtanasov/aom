package bg.autohouse.spider.client;

public abstract class AbstractHttpExecutor implements HttpExecutor
{
    protected abstract <T> Response<T> call(RequestMetadata metadata);
}
