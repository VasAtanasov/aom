package bg.autohouse.spider.client;

import java.io.InputStream;
import java.io.Serializable;

public abstract class RequestBody implements Serializable
{
    private InputStream body;
    private Header contentType;

    protected RequestBody(InputStream body, String contentType)
    {
        this.body = body;
        setContentType(contentType);
    }

    public void setContentType(Header contentType)
    {
        this.contentType = contentType;
    }

    public void setContentType(String contentType)
    {
        Header header = null;
        if (contentType != null)
        {
            header = new Header("Content-Type", contentType);
        }
        this.setContentType(header);
    }

    public InputStream getBody()
    {
        return body;
    }

    public Header getContentType()
    {
        return contentType;
    }
}
