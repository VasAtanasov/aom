package bg.autohouse.web.controllers;

import bg.autohouse.web.models.response.ApiResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.test.web.servlet.ResultMatcher;

public class ResponseBodyMatchers {
  private ObjectMapper objectMapper = new ObjectMapper();

  public ResultMatcher containsError(String expectedFieldName, String expectedMessage) {
    return mvcResult -> {
      String json = mvcResult.getResponse().getContentAsString();
      ApiResponseModel errorResult = objectMapper.readValue(json, ApiResponseModel.class);

      List<LinkedHashMap<String, String>> fieldErrors =
          (List<LinkedHashMap<String, String>>) errorResult.getErrors();
      int a = 5;

      List<String> messages =
          fieldErrors.stream()
              .flatMap(map -> map.values().stream())
              .filter(msg -> msg.equals(expectedMessage))
              .collect(Collectors.toList());
              Collectors
      // fieldErrors.stream()
      //     .filter(fieldError -> fieldError.get().equals(expectedFieldName))
      //     .filter(fieldError -> fieldError.getMessage().equals(expectedMessage))
      //     .collect(Collectors.toList());

      // assertThat(fieldErrors)
      //     .hasSize(1)
      //     .withFailMessage(
      //         "expecting exactly 1 error message" + "with field name '%s' and message '%s'",
      //         expectedFieldName, expectedMessage);
    };
  }

  static ResponseBodyMatchers responseBody() {
    return new ResponseBodyMatchers();
  }
}
