package bg.autohouse.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfiguration {
    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        Converter<LocalDateTime, String> toLocalDate = new AbstractConverter<>() {
            @Override
            protected String convert(LocalDateTime localDateTime) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
                return localDateTime == null ? null : localDateTime.format(formatter);
            }
        };

        mapper.addConverter(toLocalDate);
        return mapper;
    }
}
