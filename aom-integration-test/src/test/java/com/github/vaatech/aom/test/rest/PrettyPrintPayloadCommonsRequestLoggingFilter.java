package com.github.vaatech.aom.test.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.vaatech.aom.util.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class PrettyPrintPayloadCommonsRequestLoggingFilter extends CommonsRequestLoggingFilter {
  private final ObjectMapper objectMapper;
  private final ObjectWriter objectWriter;

  public PrettyPrintPayloadCommonsRequestLoggingFilter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
  }

  @Override
  protected String getMessagePayload(HttpServletRequest request) {
    String payload = super.getMessagePayload(request);
    if (payload != null && JsonUtil.isJSONValid(payload)) {
      try {
        Object obj = objectMapper.readValue(payload, Object.class);
        return objectWriter.writeValueAsString(obj);
      } catch (JsonProcessingException e) {
        return payload;
      }
    }
    return payload;
  }
}
