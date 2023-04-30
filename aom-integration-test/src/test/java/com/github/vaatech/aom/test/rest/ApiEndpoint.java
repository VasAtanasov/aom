package com.github.vaatech.aom.test.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public record ApiEndpoint(
    String endpointUrl,
    HttpMethod httpMethod,
    Map<String, String> requestParameters,
    Map<String, String> requestHeaders,
    Object requestBody) {

  public static final Map<String, String> DEFAULT_HEADERS = new HashMap<>();
  public static final Map<String, String> NO_REQUEST_PARAMETERS = new HashMap<>(0);

  /**
   * Constructs a new ApiEndpoint instance with the given parameters.
   *
   * @param endpointUrl the URL of the API endpoint
   * @param httpMethod the HTTP method used for the request
   * @param requestParameters a Map containing the request parameters for the request
   * @param requestHeaders a Map containing the request headers for the request
   * @param requestBody the request body for the request
   */
  public ApiEndpoint {
    if (requestParameters == null) requestParameters = new HashMap<>();
    if (requestHeaders == null) requestHeaders = new HashMap<>();
  }

  public static ApiEndpointBuilder builder() {
    return new ApiEndpointBuilder();
  }

  public static class ApiEndpointBuilder {
    private String url;
    private HttpMethod httpMethod = HttpMethod.GET;
    private Map<String, String> params = NO_REQUEST_PARAMETERS;
    private Map<String, String> headers = DEFAULT_HEADERS;
    private Object requestBody;

    public ApiEndpointBuilder url(String url) {
      this.url = url;
      return this;
    }

    public ApiEndpointBuilder httpMethod(HttpMethod httpMethod) {
      this.httpMethod = httpMethod;
      return this;
    }

    public ApiEndpointBuilder params(Map<String, String> params) {
      this.params = params;
      return this;
    }

    public ApiEndpointBuilder headers(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public ApiEndpointBuilder body(Object requestBody) {
      this.requestBody = requestBody;
      return this;
    }

    public ApiEndpoint build() {
      return new ApiEndpoint(url, httpMethod, params, headers, requestBody);
    }
  }
}
