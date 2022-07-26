package bg.autohouse.spider.client.exception;

public class HttpClientException extends RuntimeException
{

    public HttpClientException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

    public HttpClientException(String message)
    {
        super(message);
    }
}
