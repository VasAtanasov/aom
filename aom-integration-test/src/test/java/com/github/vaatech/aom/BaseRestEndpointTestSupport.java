package com.github.vaatech.aom;

//import com.github.vaatech.aom.rest.controller.error.RestError;

import com.github.vaatech.aom.test.rest.ApiEndpoint;
import com.github.vaatech.aom.test.rest.HttpClientException;
import com.github.vaatech.aom.test.rest.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class BaseRestEndpointTestSupport extends BaseApplicationTest {

    @Autowired
    TestRestTemplate restTemplate;

    protected <T> ResponseEntity<PageResponse<T>> executeForPage(
            ApiEndpoint endpoint, ParameterizedTypeReference<PageResponse<T>> responseType) {
        return executeInternal(endpoint, responseType);
    }

    protected <T> ResponseEntity<T> execute(ApiEndpoint endpoint, Class<T> responseType) {
        return executeInternal(endpoint, ParameterizedTypeReference.forType(responseType));
    }

    protected <T> ResponseEntity<T> execute(
            ApiEndpoint endpoint, ParameterizedTypeReference<T> responseType) {
        return executeInternal(endpoint, responseType);
    }

//  protected ResponseEntity<RestError> executeForError(ApiEndpoint endpoint) {
//    return execute(endpoint, RestError.class);
//  }

    private <T> ResponseEntity<T> executeInternal(
            ApiEndpoint endpoint, ParameterizedTypeReference<T> responseType) {
        String uri = getRequestUriBuilder(endpoint).toUriString();
        HttpMethod method = endpoint.httpMethod();
        HttpEntity<Object> httpEntity = getHttpEntity(endpoint);

        try {
            return handleResponseStatus(restTemplate.exchange(uri, method, httpEntity, responseType));
        } catch (Exception e) {
            throw new HttpClientException(
                    String.format("Could not perform http %s call to %s", method, uri), e);
        }
    }

    private <T> ResponseEntity<T> handleResponseStatus(ResponseEntity<T> response) {
        return response;
    }

    private HttpEntity<Object> getHttpEntity(ApiEndpoint endpoint) {
        HttpHeaders headers = new HttpHeaders();
        endpoint.requestHeaders().forEach(headers::add);
        return new HttpEntity<>(endpoint.requestBody(), headers);
    }

    private UriComponentsBuilder getRequestUriBuilder(ApiEndpoint endpoint) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(endpoint.endpointUrl());
        endpoint.requestParameters().forEach(uriBuilder::queryParam);
        return uriBuilder;
    }
}
