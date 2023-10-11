package com.github.vaatech.aom.commons.utils.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.internal.typetools.TypeResolver;
import org.modelmapper.internal.util.Assert;

/**
 * Configurer for {@link TypeMap}.
 *
 * @param <S> source type
 * @param <D> destination type
 */
public abstract class TypeMapConfigurerSupport<S, D> implements ModelMapperConfigurer {
    /**
     * Configure {@link TypeMap}.
     *
     * @param typeMap configuring {@link TypeMap}
     */
    public abstract void typeMap(TypeMap<S, D> typeMap);

    /**
     * Create or get {@link TypeMap} for generic types.
     *
     * @param modelMapper configuring {@link ModelMapper}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void configure(ModelMapper modelMapper) {
        Class<?>[] typeArguments = TypeResolver.resolveRawArguments(TypeMapConfigurerSupport.class, getClass());
        Assert.notNull(typeArguments, "Must declare source type argument <S> and destination type argument <D> for TypeMap");
        typeMap(modelMapper.typeMap((Class<S>) typeArguments[0], (Class<D>) typeArguments[1]));
    }
}