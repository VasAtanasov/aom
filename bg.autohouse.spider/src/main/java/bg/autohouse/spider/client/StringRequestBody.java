package bg.autohouse.spider.client;

import java.io.InputStream;

class StringRequestBody extends RequestBody
{

    StringRequestBody(InputStream body)
    {
        super(body, "plain/text");
    }

}
