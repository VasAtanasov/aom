package com.github.vaatech.aom.commons.utils.modelmapper;

import java.util.List;
import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

public final class MappingUtil
{
    private static ModelMapper mapper;

    public static void setMapper(ModelMapper mapper)
    {
        MappingUtil.mapper = mapper;
    }

    public static ModelMapper getMapper()
    {
        if (Objects.isNull(mapper))
        {
            return new ModelMapper();
        }
        return mapper;
    }

    public static <S, D> D map(S entity, Class<D> outClass)
    {
        Objects.requireNonNull(entity, "Source object must not be null");
        Objects.requireNonNull(outClass, "Destination type must not be null");

        return getMapper().map(entity, outClass);
    }

    public static <S, D> List<D> map(List<S> source)
    {
        Objects.requireNonNull(source, "Source object must not be null");

        return getMapper().map(source, new TypeToken<>() {}.getType());
    }

    public static <S, D> D map(S entity, D destination)
    {
        Objects.requireNonNull(entity, "Source object must not be null");
        Objects.requireNonNull(destination, "Destination object must not be null");

        getMapper().map(entity, destination);

        return destination;
    }
}