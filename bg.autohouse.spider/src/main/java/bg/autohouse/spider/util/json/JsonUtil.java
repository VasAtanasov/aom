package com.piconsult.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.piconsult.util.coverters.FormatLoader;
import com.piconsult.util.coverters.FormatLoaderFactoryDefault;
import com.piconsult.util.coverters.jackson.*;
import org.apache.log4j.Logger;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonUtil
{
    private static Logger logger = Logger.getLogger(JsonUtil.class);
    private static final String ERROR_DESERIALIZING = "Error deserializing object";
    
    private static ObjectMapper objectMapper = null;
    
    private static ObjectMapper getMapper()
    {
        if(objectMapper == null)
        {
            objectMapper = Jackson2ObjectMapperBuilder.json().build();
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            registerSerializersDeserializersModule(objectMapper);
        }
        
        return objectMapper;
    }
    
    
    private static String toJSON(Object object,  boolean ignoreNulls)
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
        ObjectWriter objectWriter;
        Object value;
        if (object instanceof MappingJacksonValue)
        {
            MappingJacksonValue container = (MappingJacksonValue) object;
            value = container.getValue();
            Class<?> serializationView = container.getSerializationView();
            objectWriter = objectMapper.writerWithView(serializationView);
        }
        else {
            objectWriter = objectMapper.writer();
            value = object;
        }

        try
        {
            return objectWriter.writeValueAsString(value);
        }
        catch (JsonProcessingException e)
        {
            logger.error(e);
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
            logger.error(ERROR_DESERIALIZING, e);
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
            logger.error(ERROR_DESERIALIZING, e);
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
            logger.error(ERROR_DESERIALIZING, e);
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
            logger.error(ERROR_DESERIALIZING, e);
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
            logger.error(ERROR_DESERIALIZING, e);
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
            logger.error(ERROR_DESERIALIZING, e);
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
    
    
    public ObjectMapper getObjectMapper()
    {
        return getMapper();
    }
    
    
    private static void registerSerializersDeserializersModule(ObjectMapper mapper)
    {
        SimpleModule module = new SimpleModule("customSer", Version.unknownVersion());
        module.addSerializer(Date.class, new JsonDateSerializer());
        module.addSerializer(Number.class, new JsonNumberSerializer());
        module.addSerializer(ZonedDateTime.class, new JsonZonedDateTimeSerializer());
        module.addSerializer(LocalDateTime.class, new JsonLocalDateTimeSerializer());
        
        FormatLoader formatLoader = FormatLoaderFactoryDefault.getInstance();
        module.addDeserializer(Date.class, new JsonDateDeserializer(formatLoader));
        module.addDeserializer(ZonedDateTime.class, new JsonZonedDateTimeDeserializer());
        module.addDeserializer(LocalDateTime.class, new JsonLocalDateTimeDeserializer());
        
        mapper.registerModule(module);
    }
   

}


