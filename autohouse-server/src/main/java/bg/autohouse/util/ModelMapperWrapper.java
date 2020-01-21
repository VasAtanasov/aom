package bg.autohouse.util;

import org.modelmapper.PropertyMap;

import java.util.Collection;
import java.util.List;

public interface ModelMapperWrapper {

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>      type of result object.
     * @param <T>      type of source object to map from.
     * @param entity   entity that needs to be mapped.
     * @param outClass class of result object.
     * @return new object of <code>outClass</code> type.
     */

    <D, T> D map(T entity, Class<D> outClass);

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>         type of result object.
     * @param <T>         type of source object to map from.
     * @param entity      entity that needs to be mapped.
     * @param outClass    class of result object.
     * @param propertyMap custom property map.
     * @return new object of <code>outClass</code> type.
     */

    <D, T> D map(T entity, Class<D> outClass, PropertyMap<T, D> propertyMap);

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param entityList list of entities that needs to be mapped
     * @param outCLass   class of result list element
     * @param <D>        type of objects in result list
     * @param <T>        type of entity in <code>entityList</code>
     * @return list of mapped object with <code><D></code> type.
     */

    <D, T> List<D> mapAll(Collection<T> entityList, Class<D> outCLass);

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param source      object to map from
     * @param destination object to map to
     */

    <S, D> D map(S source, D destination);
}
