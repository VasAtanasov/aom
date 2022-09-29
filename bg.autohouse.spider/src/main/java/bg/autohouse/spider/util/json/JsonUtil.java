package bg.autohouse.spider.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JsonUtil {
  private static final String ERROR_DESERIALIZING = "Error deserializing object";

  private static ObjectMapper objectMapper = null;

  private static ObjectMapper mapper() {
    if (objectMapper == null) {
      objectMapper = ObjectMapperFactory.mapper();
    }

    return objectMapper;
  }

  private static String toJSON(Object object, boolean ignoreNulls) {
    if (ignoreNulls) {
      ObjectMapper mapper = mapper().copy();
      mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
      return toJSON(object, mapper);
    }

    return toJSON(object, mapper());
  }

  private static String toJSON(Object object, ObjectMapper objectMapper) {
    ObjectWriter objectWriter = objectMapper.writer();
    try {
      return objectWriter.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error(e.toString());
    }
    return null;
  }

  public static String toJSON(Object object) {
    return toJSON(object, false);
  }

  public static String toJSONNoNulls(Object object) {
    return toJSON(object, true);
  }

  public static Object fromJSON(String input) {
    return fromJSON(input, Object.class);
  }

  public static <T> T fromJSON(String input, Class<T> clazz) {
    try {

      return mapper().readValue(input, clazz);
    } catch (IOException e) {
      log.error(ERROR_DESERIALIZING, e);
    }
    return null;
  }

  public static <T> T fromJSON(InputStream input, Class<T> clazz) {
    try {

      return mapper().readValue(input, clazz);
    } catch (IOException e) {
      log.error(ERROR_DESERIALIZING, e);
    }
    return null;
  }

  public static <T> T fromJSON(InputStream input, TypeReference<T> clazz) {
    try {

      return mapper().readValue(input, clazz);
    } catch (IOException e) {
      log.error(ERROR_DESERIALIZING, e);
    }
    return null;
  }

  public static <T> T fromJSON(String input, TypeReference<T> reference) {
    try {
      return mapper().readValue(input, reference);
    } catch (IOException e) {
      log.error(ERROR_DESERIALIZING, e);
    }
    return null;
  }

  public static <T> List<T> fromJSONList(String input, Class<T> clazz) {
    try {
      return mapper()
          .readValue(input, mapper().getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (IOException e) {
      log.error(ERROR_DESERIALIZING, e);
    }
    return Collections.emptyList();
  }

  public static <T> T fromJSON(String input, JavaType javaType) {
    try {
      return mapper().readValue(input, javaType);
    } catch (IOException e) {
      log.error(ERROR_DESERIALIZING, e);
    }
    return null;
  }

  public static boolean isJSONValid(String jsonInString) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.readTree(jsonInString);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
