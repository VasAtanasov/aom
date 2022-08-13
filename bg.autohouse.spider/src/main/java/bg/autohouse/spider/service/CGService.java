package bg.autohouse.spider.service;

public interface CGService extends AutoCloseable {

  @Override
  default void close() {}
}
