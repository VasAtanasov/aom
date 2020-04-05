package bg.autohouse.web.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.web.models.response.ResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.test.web.servlet.ResultMatcher;

public class ResponseBodyMatchers {
  private ObjectMapper objectMapper = new ObjectMapper();

  @SuppressWarnings("unchecked")
  public ResultMatcher containsError(String expectedFieldName, String expectedMessage) {
    return mvcResult -> {
      String json = mvcResult.getResponse().getContentAsString();
      ResponseWrapper errorResult = objectMapper.readValue(json, ResponseWrapper.class);

      List<LinkedHashMap<String, String>> fieldErrors =
          (List<LinkedHashMap<String, String>>) errorResult.getErrors();

      List<String> messages =
          fieldErrors.stream()
              .flatMap(map -> map.values().stream())
              .filter(msg -> msg.equals(expectedMessage))
              .collect(Collectors.toList());

      assertThat(messages)
          .hasSize(1)
          .withFailMessage(
              "expecting exactly 1 error message" + "with field name '%s' and message '%s'",
              expectedFieldName, expectedMessage);
    };
  }

  static ResponseBodyMatchers responseBody() {
    return new ResponseBodyMatchers();
  }
}
