package bg.autohouse;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
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
  }

  private MockHttpServletRequestBuilder call(HttpMethod method, String url, HttpHeaders headers) {
    return methodsMap
        .get(method)
        .apply(url)
        .headers(headers)
        .contentType(APP_V1_MEDIA_TYPE_JSON)
        .accept(APP_V1_MEDIA_TYPE_JSON);
  }

  public ResultActions performPost(final String url, final Object object) throws Exception {
    return performPost(url, object, new HttpHeaders());
  }

  public ResultActions performPost(final String url, final Object object, HttpHeaders headers)
      throws Exception {
    return getMockMvc()
        .perform(call(HttpMethod.POST, url, headers).content(asJsonString(object)))
        .andDo(print());
  }

  public ResultActions performPut(final String url, final Object object) throws Exception {
    return performPut(url, object, new HttpHeaders());
  }

  public ResultActions performPut(final String url, final Object object, HttpHeaders headers)
      throws Exception {
    return getMockMvc()
        .perform(call(HttpMethod.PUT, url, headers).content(asJsonString(object)))
        .andDo(print());
  }

  public ResultActions performGet(final String url) throws Exception {
    return performGet(url, new HttpHeaders());
  }

  public ResultActions performGet(final String url, HttpHeaders headers) throws Exception {
    return getMockMvc().perform(call(HttpMethod.GET, url, headers)).andDo(print());
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
}
