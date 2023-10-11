package com.github.vaatech.aom.bl;

import com.github.vaatech.aom.commons.basic.dto.BaseDTO;
import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import com.github.vaatech.aom.core.repository.ApplicationJpaRepository;
import jakarta.annotation.Nonnull;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractCRUDService<ID extends Serializable, E extends BaseEntity<ID>, D extends BaseDTO<ID>>
        implements CRUDService<ID, E, D> {

    protected abstract ApplicationJpaRepository<E, ID> getRepository();

    public Class<E> getClazz() {
        return getRepository().getDomainClass();
    }

    protected abstract void updateEntity(E entity, D dto);

    protected abstract D toDTO(@Nonnull E entity);

    @Override
    @Transactional(readOnly = true)
    public D read(ID id) {
        return toDTO(requireEntity(id));
    }

    @Override
    @Transactional
    public D create(D dto) {
        final E entity = createEntity();

        updateEntity(entity, dto);

        final E persisted = getRepository().saveAndFlush(entity);

        afterCreate(persisted, dto);
        return toDTO(persisted);
    }

    protected void afterCreate(E entity, D dto) {
    }

    protected void afterUpdate(E entity, D dto) {
    }

    private E createEntity() {
        try {
            final Constructor<E> defaultConstructor = getClazz().getConstructor();
            return defaultConstructor.newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException("Could not create entity", e);
        }
    }

    @Override
    @Transactional
    public D update(D dto) {
        final E entity = requireEntity(dto.getId());

        updateEntity(entity, dto);

        final E persisted = getRepository().saveAndFlush(entity);
        afterUpdate(persisted, dto);
        return toDTO(persisted);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        final E entity = requireEntity(id);
        delete(entity);
    }

    protected void delete(E entity) {
        getRepository().delete(entity);
    }

    protected E requireEntity(final ID id) {
        Objects.requireNonNull(id, "Entity primary key is required");
        return getRepository()
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such " + getClazz().getCanonicalName() + " id=" + id));
    }
}
