package bg.autohouse.spider.api;

public interface ApiClient {
  HttpStrategy http();

  String apiBaseUrl();
}
