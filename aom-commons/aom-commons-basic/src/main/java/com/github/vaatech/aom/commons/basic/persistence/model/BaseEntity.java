package com.github.vaatech.aom.commons.basic.persistence.model;

import jakarta.persistence.Transient;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable, Identifiable<T>, Persistable<T>, HasVersion {
    @Override
    @Transient
    default boolean isNew() {
        return null == getId();
    }
}
