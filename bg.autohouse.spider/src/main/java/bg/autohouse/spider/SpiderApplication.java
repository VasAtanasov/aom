package bg.autohouse.spider;

import bg.autohouse.spider.api.HttpStrategy;
import bg.autohouse.spider.client.CGApiClientAdapterImpl;
import bg.autohouse.spider.client.CGApiClientImpl;
import bg.autohouse.spider.client.JavaHttpClientStrategy;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import bg.autohouse.spider.service.AppConfigurationService;
import bg.autohouse.spider.service.CGServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SpiderApplication {
  public static void main(String[] args) {

    log.info("Running SpiderApplication");
    log.info("Initializing CG Client");

    AppConfigurationService.create();
    HttpStrategy httpStrategy = new JavaHttpClientStrategy();
    CGApiClientImpl cg = new CGApiClientImpl(httpStrategy);
    log.info("CG base url: " + cg.apiBaseUrl());
    CGApiClientAdapterImpl cgAdapter = new CGApiClientAdapterImpl(cg);

    //    try (SpiderCacheManager cacheManager = new SpiderCacheManager()) {
    //      CGApiClientCacheProxy proxy = new CGApiClientCacheProxy(cgAdapter, cacheManager);
    //      CGService service = new CGService(cgAdapter);
    //      service.crawl();
    //    }
    try (CGServiceImpl service = new CGServiceImpl(cgAdapter, 3)) {
      //            var trims = service.updateMakersModels();

      List<MakerDTO> makers =
          service.fetchMakers().stream().filter(MakerDTO::isPopular).sorted().skip(0).limit(3).toList();
      var listings = service.fetchListings(makers);
    }
  }
}
