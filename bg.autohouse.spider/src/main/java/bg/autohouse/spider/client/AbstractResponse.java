package bg.autohouse.spider.client;

abstract class AbstractResponse<T>
{
    private final String url;
    private final int statusCode;
    private final T body;

    protected AbstractResponse(String url, int statusCode, T body)
    {
        this.url = url;
        this.statusCode = statusCode;
        this.body = body;
    }

    public String url()
    {
        return url;
    }

    public int statusCode()
    {
        return statusCode;
    }

    public T body()
    {
        return body;
    }
}
