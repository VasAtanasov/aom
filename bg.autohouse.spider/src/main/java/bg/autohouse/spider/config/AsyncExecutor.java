package bg.autohouse.spider.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecutor {
  private static ExecutorService pool;
  private static AsyncExecutor instance;

  private AsyncExecutor(int maxTherads) {
    pool = Executors.newFixedThreadPool(maxTherads);
  }

  public static AsyncExecutor create(int maxTherads) {
    if (instance == null || pool.isTerminated()) {
      instance = new AsyncExecutor(maxTherads);
    }
    return instance;
  }

  public void destroy() {
    pool.shutdown();
  }

  public void destroyNow() {
    pool.shutdownNow();
  }
}
