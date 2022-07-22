package bg.autohouse.spider.util.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ObjectMapperFactory {
  private static ObjectMapper objectMapper;

  public static ObjectMapper mapper() {

    if (objectMapper == null) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      mapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
      mapper.setDateFormat(getISO8601DateFormat());
      mapper.registerModule(new JavaTimeModule());

      objectMapper = mapper;
    }

    return objectMapper;
  }

  private static DateFormat getISO8601DateFormat() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    df.setTimeZone(TimeZone.getTimeZone("UTC"));
    return df;
  }

  private ObjectMapperFactory() {}
}
