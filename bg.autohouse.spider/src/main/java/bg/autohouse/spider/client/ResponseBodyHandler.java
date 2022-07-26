package bg.autohouse.spider.client;

import java.io.InputStream;

@FunctionalInterface
public interface ResponseBodyHandler<T>
{
    T handle(InputStream inputStream);
}
