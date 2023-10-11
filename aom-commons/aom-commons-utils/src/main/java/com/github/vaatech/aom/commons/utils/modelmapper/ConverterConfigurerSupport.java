package com.github.vaatech.aom.commons.utils.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

/**
 * An {@link Converter} model mapper configurer. This template method class allows to specify
 * the {@link Converter} that will be registered within the model mapper.
 *
 * @param <S> the source object type
 * @param <D> the destination object type
 */
public abstract class ConverterConfigurerSupport<S, D> implements ModelMapperConfigurer {

    /**
     * Allows to specify a custom converter between two types.
     *
     * @return the converter
     */
    protected abstract Converter<S, D> converter();

    /**
     * Configures the passed {@link ModelMapper} instance by registering the {@link Converter} defined by
     * {@link #converter()} method.
     *
     * @param modelMapper {@link ModelMapper} instance to be configured
     */
    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper.addConverter(converter());
    }
}