package bg.autohouse.spider.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
public class JsonUtil
{
    private static final String ERROR_DESERIALIZING = "Error deserializing object";

    private static ObjectMapper objectMapper = null;

    private static ObjectMapper getMapper()
    {
        if (objectMapper == null)
        {
            objectMapper = ObjectMapperFactory.mapper();
        }

        return objectMapper;
    }


    private static String toJSON(Object object, boolean ignoreNulls)
    {
        if (ignoreNulls)
        {
            ObjectMapper mapper = getMapper().copy();

            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            return toJSON(object, mapper);
        }

        return toJSON(object, getMapper());
    }

    private static String toJSON(Object object, ObjectMapper objectMapper)
    {
        ObjectWriter objectWriter = objectMapper.writer();
        try
        {
            return objectWriter.writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
            log.error(e.toString());
        }
        return null;
    }

    public static String toJSON(Object object)
    {
        return toJSON(object, false);
    }

    public static String toJSONNoNulls(Object object)
    {
        return toJSON(object, true);
    }


    public static Object fromJSON(String input)
    {
        return fromJSON(input, Object.class);
    }

    public static <T> T fromJSON(String input, Class<T> clazz)
    {
        try
        {

            return getMapper().readValue(input, clazz);
        }
        catch (IOException e)
        {
            log.error(ERROR_DESERIALIZING, e);
        }
        return null;
    }

    public static <T> T fromJSON(InputStream input, Class<T> clazz)
    {
        try
        {

            return getMapper().readValue(input, clazz);
        }
        catch (IOException e)
        {
            log.error(ERROR_DESERIALIZING, e);
        }
        return null;
    }

    public static <T> T fromJSON(InputStream input, TypeReference<T> clazz)
    {
        try
        {

            return getMapper().readValue(input, clazz);
        }
        catch (IOException e)
        {
            log.error(ERROR_DESERIALIZING, e);
        }
        return null;
    }

    public static <T> T fromJSON(String input, TypeReference<T> reference)
    {
        try
        {
            return getMapper().readValue(input, reference);
        }
        catch (IOException e)
        {
            log.error(ERROR_DESERIALIZING, e);
        }
        return null;
    }

    public static List<Object> fromJSONList(String input)
    {
        try
        {
            return getMapper().readValue(input, List.class);
        }
        catch (IOException e)
        {
            log.error(ERROR_DESERIALIZING, e);
        }
        return Collections.emptyList();
    }

    public static <T> List<T> fromJSONList(String input, Class<T> clazz)
    {
        try
        {
            return getMapper().readValue(input, getMapper().getTypeFactory().constructCollectionType(List.class, clazz));
        }
        catch (IOException e)
        {
            log.error(ERROR_DESERIALIZING, e);
        }
        return Collections.emptyList();
    }

    public static <T> T fromJSON(String input, JavaType javaType)
    {
        try
        {
            return getMapper().readValue(input, javaType);
        }
        catch (IOException e)
        {
            log.error(ERROR_DESERIALIZING, e);
        }
        return null;
    }

    public static <T> Map<T, Object> fromJSONMap(String input)
    {
        try
        {
            return getMapper().readValue(input, Map.class);
        }
        catch (IOException e)
        {
            log.error(ERROR_DESERIALIZING, e);
        }
        return null;
    }

    public static boolean isJSONValid(String jsonInString)
    {
        try
        {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }


}


