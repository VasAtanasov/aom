package bg.autohouse.integration.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//@Configuration
//public class HttpClientConfiguration {
//  private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
//
//  private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
//
//  private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (10 * 1000);
//
//  @Bean
//  public ClientHttpRequestFactory httpRequestFactory(CloseableHttpClient httpClient) {
//    return new HttpComponentsClientHttpRequestFactory(httpClient);
//  }
//
//  @Bean
//  public RestTemplate defaultRestTemplate(ClientHttpRequestFactory httpRequestFactory) {
//    return new RestTemplate(httpRequestFactory);
//  }
//
//  @Bean
//  public CloseableHttpClient httpClient() {
//    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//    connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
//    connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
//    RequestConfig config =
//        RequestConfig.custom().setConnectTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS).build();
//
//    return HttpClientBuilder.create()
//        .setConnectionManager(connectionManager)
//        .setDefaultRequestConfig(config)
//        .build();
//  }
//}
