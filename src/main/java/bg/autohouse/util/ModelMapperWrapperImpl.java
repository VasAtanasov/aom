package bg.autohouse.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class ModelMapperWrapperImpl implements ModelMapperWrapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ModelMapperWrapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    @Override
    public <D, T> D map(T entity, Class<D> outClass, PropertyMap<T, D> propertyMap) {
        this.modelMapper.addMappings(propertyMap);
        return this.map(entity, outClass);
    }

    @Override
    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    @Override
    public <S, D> D map(final S source, D destination) {
        this.modelMapper.map(source, destination);
        return destination;
    }
}