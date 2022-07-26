package bg.autohouse.spider.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class RawResponse extends AbstractResponse implements AutoCloseable
{
    private final InputStream body;

    protected RawResponse(String url, int statusCode, InputStream body)
    {
        super(url, statusCode);
        this.body = body;
    }

    public InputStream body()
    {
        return body;
    }

    @Override
    public void close()
    {
        try
        {
            this.body.close();
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }
}
