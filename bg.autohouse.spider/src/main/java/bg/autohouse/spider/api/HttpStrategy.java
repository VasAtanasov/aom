package bg.autohouse.spider.api;

import bg.autohouse.spider.client.RequestMetadata;
import bg.autohouse.spider.client.Response;
import java.util.concurrent.CompletableFuture;

public interface HttpStrategy {
  default <T> Response<T> call(RequestMetadata metadata, ResponseBodyHandler<T> handler) {
    throw new UnsupportedOperationException("Synchronous operation not supported");
  }

  default <T> CompletableFuture<Response<T>> callAsync(
      RequestMetadata metadata, ResponseBodyHandler<T> handler) {
    throw new UnsupportedOperationException("Async operation not supported");
  }
}
