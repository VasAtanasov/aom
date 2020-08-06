package bg.autohouse;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import bg.autohouse.web.models.request.UserLoginRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public abstract class MvcPerformer {

  public abstract MockMvc getMockMvc();

  private static final Map<HttpMethod, Function<String, MockHttpServletRequestBuilder>> methodsMap =
      new HashMap<>();

  static {
    methodsMap.put(HttpMethod.POST, url -> post(url));
    methodsMap.put(HttpMethod.GET, url -> get(url));
    methodsMap.put(HttpMethod.PUT, url -> put(url));
    methodsMap.put(HttpMethod.DELETE, url -> delete(url));
  }

  private MockHttpServletRequestBuilder call(HttpMethod method, String url, HttpHeaders headers) {
    return methodsMap
        .get(method)
        .apply(url)
        .headers(headers)
        .contentType(APP_V1_MEDIA_TYPE_JSON)
        .accept(APP_V1_MEDIA_TYPE_JSON);
  }

  private MockHttpServletRequestBuilder callMultiPart(
      HttpMethod method, String url, HttpHeaders headers) {
    return methodsMap
        .get(method)
        .apply(url)
        .headers(headers)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE);
  }

  public ResultActions performPost(final String url, final Object object) throws Exception {
    return performPost(url, object, new HttpHeaders());
  }

  public ResultActions performPost(final String url, final Object object, HttpHeaders headers)
      throws Exception {
    return getMockMvc().perform(call(HttpMethod.POST, url, headers).content(asJsonString(object)));
  }

  public ResultActions performPostFormData(
      final String url, final Object object, HttpHeaders headers) throws Exception {
    return getMockMvc()
        .perform(callMultiPart(HttpMethod.POST, url, headers).content(asJsonString(object)));
  }

  public ResultActions performPut(final String url, final Object object) throws Exception {
    return performPut(url, object, new HttpHeaders());
  }

  public ResultActions performPut(final String url, final Object object, HttpHeaders headers)
      throws Exception {
    return getMockMvc().perform(call(HttpMethod.PUT, url, headers).content(asJsonString(object)));
  }

  public ResultActions performGet(final String url) throws Exception {
    return performGet(url, new HttpHeaders());
  }

  public ResultActions performGet(final String url, HttpHeaders headers) throws Exception {
    return getMockMvc().perform(call(HttpMethod.GET, url, headers));
  }

  public ResultActions performDelete(final String url, HttpHeaders headers) throws Exception {
    return getMockMvc().perform(call(HttpMethod.DELETE, url, headers));
  }

  public ResultActions performDelete(final String url) throws Exception {
    return getMockMvc().perform(call(HttpMethod.DELETE, url, new HttpHeaders()));
  }

  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      return mapper.writeValueAsString(obj);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T convertJSONStringToObject(String json, Class<T> objectClass)
      throws JsonMappingException, JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    JavaTimeModule module = new JavaTimeModule();
    mapper.registerModule(module);
    return mapper.readValue(json, objectClass);
  }

  public class HeadersBuilder {
    private Map<String, String> headers = new HashMap<>();

    public HeadersBuilder add(String name, String value) {
      headers.put(name, value);
      return this;
    }

    public HttpHeaders build() {
      HttpHeaders httpHeaders = new HttpHeaders();
      for (Entry<String, String> entry : headers.entrySet()) {
        httpHeaders.add(entry.getKey(), entry.getValue());
      }
      return httpHeaders;
    }
  }

  public HttpHeaders getAuthHeadersFor(UserLoginRequest loginRequest) throws Exception {
    MvcResult mvcResult = performPost("/api/auth/login", loginRequest).andReturn();
    String authHeader = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    HttpHeaders headers = new HttpHeaders();
    if (authHeader != null) headers.add(HttpHeaders.AUTHORIZATION, authHeader);
    return headers;
  }
}
