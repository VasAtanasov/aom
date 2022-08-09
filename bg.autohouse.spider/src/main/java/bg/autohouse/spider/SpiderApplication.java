package bg.autohouse.spider;

import bg.autohouse.spider.api.HttpStrategy;
import bg.autohouse.spider.client.CGApiClientAdapterImpl;
import bg.autohouse.spider.client.CGApiClientImpl;
import bg.autohouse.spider.client.JavaHttpClientStrategy;
import bg.autohouse.spider.service.AppConfigurationService;
import bg.autohouse.spider.service.CGService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpiderApplication {
  public static void main(String[] args) {

    log.info("Running SpiderApplication");
    log.info("Initializing CG Client");

    AppConfigurationService.create();
    HttpStrategy httpStrategy = new JavaHttpClientStrategy();
    CGApiClientImpl cg = new CGApiClientImpl(httpStrategy);
    CGApiClientAdapterImpl cgAdapter = new CGApiClientAdapterImpl(cg);
    //    try (SpiderCacheManager cacheManager = new SpiderCacheManager()) {
    //      CGApiClientCacheProxy proxy = new CGApiClientCacheProxy(cgAdapter, cacheManager);
    //      CGService service = new CGService(cgAdapter);
    //      service.crawl();
    //    }
    try (CGService service = new CGService(cgAdapter, 5)) {
      var trims = service.updateMakersModels();
      int a = 5;
    }
  }
}
