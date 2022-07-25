package bg.autohouse.spider.client;

public abstract class AbstractHttpExecutorStrategy implements HttpExecutor
{


    protected <T> Response<T> call(RequestMetadata metadata)
    {
        Request request = buildRequest(metadata);

        return null;
    }

    protected abstract Request buildRequest(RequestMetadata metadata);
}
