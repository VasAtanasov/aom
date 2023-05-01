package com.github.vaatech.aom.config;

import com.github.vaatech.aom.config.jackson.CustomJacksonObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

//@Configuration
public class SerializationConfiguration {

//  @Bean
  public CustomJacksonObjectMapper globalJacksonObjectMapper(
      @Value("${app.dev.mode}") final boolean developmentEnvironment) {
    return new CustomJacksonObjectMapper(developmentEnvironment);
  }

//  @Bean
  public MappingJackson2HttpMessageConverter jacksonMessageConverter(
      CustomJacksonObjectMapper jsonObjectMapper) {
    final MappingJackson2HttpMessageConverter jsonMessageConverter =
        new MappingJackson2HttpMessageConverter();
    jsonMessageConverter.setObjectMapper(jsonObjectMapper);
    return jsonMessageConverter;
  }
}
