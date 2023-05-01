package com.github.vaatech.aom.config.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.Page;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class CustomJacksonObjectMapper extends ObjectMapper {

  public CustomJacksonObjectMapper(boolean developmentEnvironment) {
    super();

    setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    setSerializationInclusion(JsonInclude.Include.NON_NULL);
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .setDateFormat(getISO8601DateFormat())
        .registerModule(new JavaTimeModule());

    // Only map fields and ignore get/set methods
    //    setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
    //    setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    configure(SerializationFeature.INDENT_OUTPUT, developmentEnvironment);

    // Export dates as strings
    //    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Ignore unknown properties if in production
    //    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, developmentEnvironment);
    //    configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, developmentEnvironment);

    //    configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
    // !developmentEnvironment);

    // Allow returning Spring Data JPA Page<?> from controller
//    final SimpleModule module = new SimpleModule();
//    module.addAbstractTypeMapping(Page.class, PageResource.class);
//    registerModule(module);
  }

  private static DateFormat getISO8601DateFormat() {
    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    df.setTimeZone(TimeZone.getTimeZone("UTC"));

    return df;
  }
}
