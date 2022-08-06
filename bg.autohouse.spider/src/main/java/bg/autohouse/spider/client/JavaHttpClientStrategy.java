package bg.autohouse.spider.client;

import bg.autohouse.spider.api.HttpStrategy;
import bg.autohouse.spider.api.ResponseBodyHandler;
import bg.autohouse.spider.client.exception.BadRequestException;
import bg.autohouse.spider.client.exception.ForbiddenException;
import bg.autohouse.spider.client.exception.HttpClientException;
import bg.autohouse.spider.client.exception.InternalException;
import bg.autohouse.spider.client.exception.MaintenanceException;
import bg.autohouse.spider.client.exception.NotFoundException;
import bg.autohouse.spider.client.exception.RedirectException;
import bg.autohouse.spider.client.exception.TimeoutException;
import bg.autohouse.spider.client.exception.UnauthorizedException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class JavaHttpClientStrategy implements HttpStrategy {
  private static final Logger LOG = LoggerFactory.getLogger(JavaHttpClientStrategy.class);

  private final HttpClient http =
      HttpClient.newBuilder()
          .version(Version.HTTP_1_1)
          .followRedirects(HttpClient.Redirect.NORMAL)
          .connectTimeout(Duration.ofSeconds(20))
          .build();

  private static void logRequest(String method, URI request) {
    LOG.info("Http {} Request to URL: {}", method, request);
  }

  @Override
  public <T> Response<T> call(final RequestMetadata metadata, ResponseBodyHandler<T> handler) {
    HttpRequest request = builder(metadata).build();
    HttpResponse<InputStream> response;

    //        for (int i = 0; i < metadata.retries() + 1; i++)
    //        {
    try {
      response = http.send(request, HttpResponse.BodyHandlers.ofInputStream());

      return handleResponseStatus(toResponse(handler).apply(response));

    } catch (Exception e) {
      throw new HttpClientException(
          String.format("Failed to send http %s to %s", metadata.method(), metadata.uri()), e);
    }
    //        }
  }

  @Override
  public <T> CompletableFuture<Response<T>> callAsync(
      RequestMetadata metadata, ResponseBodyHandler<T> handler) {
    HttpRequest request = builder(metadata).build();
    return http.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
        .thenApply(response -> toResponse(handler).apply(response))
        .thenApply(this::handleResponseStatus);
  }

  public HttpRequest.Builder builder(RequestMetadata metadata) {
    HttpRequest.Builder builder =
        HttpRequest.newBuilder(metadata.uri())
            .timeout(Duration.ofMillis(metadata.connectTimeout()));

    switch (metadata.method()) {
      case GET -> builder.GET();
      case POST -> {
        builder.header(metadata.contentType().name(), metadata.contentType().value());
        builder.POST(HttpRequest.BodyPublishers.ofInputStream(metadata::body));
      }
      default -> throw new UnsupportedOperationException(
          "Unsupported method: " + metadata.method());
    }

    return builder;
  }

  private <T> Response<T> handleResponseStatus(Response<T> response) {

    LOG.info("Response Status: {}", response.statusCode());

    int statusCode = response.statusCode();
    if (response.statusCode() >= 200 && response.statusCode() < 300) {
      return response;
    } else if (statusCode >= 300 && statusCode < 400) {
      throw new RedirectException();
    } else if (statusCode == 400) {
      throw new BadRequestException();
    } else if (statusCode == 401) {
      throw new UnauthorizedException();
    } else if (statusCode == 403) {
      throw new ForbiddenException();
    } else if (statusCode == 404) {
      throw new NotFoundException();
    } else if (statusCode == 408) {
      throw new TimeoutException();
    } else if (statusCode == 503) {
      throw new MaintenanceException();
    } else {
      throw new InternalException();
    }
  }

  private <T> Function<HttpResponse<InputStream>, Response<T>> toResponse(
      ResponseBodyHandler<T> handler) {
    return (response) -> {
      logRequest(response.request().method(), response.uri());
      T result = handler.handle(response.body());
      return new Response<>(response.uri().toString(), response.statusCode(), result);
    };
  }
}
