package bg.autohouse.spider.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecutor {
  private static ExecutorService pool;
  private static AsyncExecutor instance;

  private AsyncExecutor(int maxThreads) {
    pool = Executors.newFixedThreadPool(maxThreads);
  }

  public static AsyncExecutor create(int maxThreads) {
    if (instance == null || pool.isTerminated()) {
      instance = new AsyncExecutor(maxThreads);
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
