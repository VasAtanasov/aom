package bg.autohouse.spider.client.api;

import java.util.List;

public interface GetCollectionOperation<T>
{
    List<T> httpCollectionGet();
}
