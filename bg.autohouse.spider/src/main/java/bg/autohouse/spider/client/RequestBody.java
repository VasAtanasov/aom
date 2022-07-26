package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.MediaType;

import java.io.InputStream;
import java.io.Serializable;

public abstract class RequestBody implements Serializable
{
    private static final String HEADER_NAME = "Content-Type";

    private final InputStream body;
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
            header = new Header(HEADER_NAME, contentType);
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

    public static class JsonRequestBody extends RequestBody
    {
        JsonRequestBody(InputStream body)
        {
            super(body, MediaType.APPLICATION_JSON_VALUE);
        }
    }

    public static class StringRequestBody extends RequestBody
    {
        StringRequestBody(InputStream body)
        {
            super(body, MediaType.APPLICATION_TEXT_VALUE);
        }
    }
}
