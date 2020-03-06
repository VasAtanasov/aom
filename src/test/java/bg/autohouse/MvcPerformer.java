package bg.autohouse;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class MvcPerformer {
  @Autowired protected MockMvc mockMvc;

  public ResultActions performPost(final String url, final Object object) throws Exception {
    return mockMvc
        .perform(
            post(url)
                .content(asJsonString(object))
                .contentType(APP_V1_MEDIA_TYPE_JSON)
                .accept(APP_V1_MEDIA_TYPE_JSON))
        .andDo(print());
  }

  public ResultActions performPut(final String url, final Object object) throws Exception {
    return mockMvc
        .perform(
            put(url)
                .content(asJsonString(object))
                .contentType(APP_V1_MEDIA_TYPE_JSON)
                .accept(APP_V1_MEDIA_TYPE_JSON))
        .andDo(print());
  }

  public ResultActions performGet(final String url) throws Exception {
    return mockMvc.perform(get(url).accept(APP_V1_MEDIA_TYPE_JSON)).andDo(print());
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
}
