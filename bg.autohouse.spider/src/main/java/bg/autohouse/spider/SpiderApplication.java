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
    log.info("CG base url: " + cg.apiBaseUrl());
    CGApiClientAdapterImpl cgAdapter = new CGApiClientAdapterImpl(cg);

    //    Response<List<ListingDTO>> response =
    //        cg.listingsSearch()
    //            .GET(
    //                QueryParameter.of("offset", "0"),
    //                QueryParameter.of("maxResults", "35"),
    //                QueryParameter.of("entitySelectingHelper.selectedEntity", "m48"));
    //    List<ListingDTO> body = response.body();

    //    var res = cg.listingDetail().GET(QueryParameter.of("inventoryListing", "331451311"));
    //    var resBody = res.body();
    //    int a = 5;

    //    try (SpiderCacheManager cacheManager = new SpiderCacheManager()) {
    //      CGApiClientCacheProxy proxy = new CGApiClientCacheProxy(cgAdapter, cacheManager);
    //      CGService service = new CGService(cgAdapter);
    //      service.crawl();
    //    }
    try (CGService service = new CGService(cgAdapter, 5)) {
      //      var trims = service.updateMakersModels();
      var listings = service.fetchListings();
    }
  }
}
