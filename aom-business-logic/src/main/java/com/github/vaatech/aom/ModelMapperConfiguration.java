package com.github.vaatech.aom;

import static org.modelmapper.config.Configuration.AccessLevel.PACKAGE_PRIVATE;

import com.github.vaatech.aom.commons.utils.modelmapper.MappingUtil;
import com.github.vaatech.aom.commons.utils.modelmapper.ModelMapperConfigurer;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ModelMapperConfiguration {

    public static ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PACKAGE_PRIVATE)
                .setMethodAccessLevel(PACKAGE_PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapper(ObjectProvider<List<ModelMapperConfigurer>> configurersProvider) {
        log.info("Configure ModelMapper with ModelMapperConfiguration.");
        final ModelMapper modelMapper = createModelMapper();

        configurersProvider.ifAvailable(
                configurers -> configurers.forEach(c -> c.configure(modelMapper)));

        modelMapper.validate();
        log.info("Validate ModelMapper Configuration succeed.");
        loggingConfiguration(modelMapper);
        MappingUtil.setMapper(modelMapper);
        return modelMapper;
    }

    private void loggingConfiguration(ModelMapper modelMapper) {
        org.modelmapper.config.Configuration configuration = modelMapper.getConfiguration();
        log.info(
                "ModelMapper Configuration ==========================================================================");
        log.info(" SourceNameTokenizer : {}", configuration.getSourceNameTokenizer());
        log.info(" SourceNameTransformer : {}", configuration.getSourceNameTransformer());
        log.info(" SourceNamingConvention : {}", configuration.getSourceNamingConvention());
        log.info(" DestinationNameTokenizer : {}", configuration.getDestinationNameTokenizer());
        log.info(" DestinationNameTransformer : {}", configuration.getDestinationNameTransformer());
        log.info(" DestinationNameTransformer : {}", configuration.getDestinationNameTransformer());
        log.info(" DestinationNamingConvention : {}", configuration.getDestinationNamingConvention());
        log.info(" MatchingStrategy : {}", configuration.getMatchingStrategy());
        log.info(" FieldAccessLevel : {}", configuration.getFieldAccessLevel());
        log.info(" MethodAccessLevel : {}", configuration.getMethodAccessLevel());
        log.info(" FieldMatchingEnabled : {}", configuration.isFieldMatchingEnabled());
        log.info(" AmbiguityIgnored : {}", configuration.isAmbiguityIgnored());
        log.info(" FullTypeMatchingRequired : {}", configuration.isFullTypeMatchingRequired());
        log.info(" ImplicitMappingEnabled : {}", configuration.isImplicitMappingEnabled());
        log.info(" SkipNullEnabled : {}", configuration.isSkipNullEnabled());
        log.info(" CollectionsMergeEnabled : {}", configuration.isCollectionsMergeEnabled());
        log.info(" UseOSGiClassLoaderBridging : {}", configuration.isUseOSGiClassLoaderBridging());
        log.info(" DeepCopyEnabled : {}", configuration.isDeepCopyEnabled());
        log.info(" Provider : {}", configuration.getProvider());
        log.info(" PropertyCondition : {}", configuration.getPropertyCondition());
        log.info(" TypeMaps :");
        modelMapper.getTypeMaps().forEach(typeMap -> log.trace("  {}", typeMap));
        log.info(" Converters :");
        configuration.getConverters().forEach(converter -> log.trace("  {}", converter));
        log.info(
                "====================================================================================================");
    }
}
