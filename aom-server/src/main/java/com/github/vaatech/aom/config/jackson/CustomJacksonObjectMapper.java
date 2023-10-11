package com.github.vaatech.aom.config.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

        configure(SerializationFeature.INDENT_OUTPUT, developmentEnvironment);
    }

    private static DateFormat getISO8601DateFormat() {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df;
    }
}
