package bg.autohouse.spider.client;

public interface HttpExecutor<T> {
  Response<T> execute(Request request);
}
