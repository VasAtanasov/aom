package bg.autohouse.spider.client;

public abstract class AbstractResponse
{
    protected final String url;
    protected final int statusCode;

    protected AbstractResponse(String url, int statusCode)
    {
        this.url = url;
        this.statusCode = statusCode;
    }

    public String url()
    {
        return url;
    }

    public int statusCode()
    {
        return statusCode;
    }
}
