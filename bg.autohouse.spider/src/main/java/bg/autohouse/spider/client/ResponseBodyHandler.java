package bg.autohouse.spider.client;

import java.io.InputStream;
import java.nio.charset.Charset;

@FunctionalInterface
public interface ResponseBodyHandler<T>
{
    T handle(InputStream inputStream);
}
