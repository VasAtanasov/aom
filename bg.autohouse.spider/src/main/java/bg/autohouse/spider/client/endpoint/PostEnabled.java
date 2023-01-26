package bg.autohouse.spider.client.endpoint;

import bg.autohouse.spider.client.QueryParameter;
import bg.autohouse.spider.client.RequestBody;
import bg.autohouse.spider.client.Response;

public interface PostEnabled<T> {
  Response<T> POST(RequestBody body, QueryParameter... queryParameters);
}
