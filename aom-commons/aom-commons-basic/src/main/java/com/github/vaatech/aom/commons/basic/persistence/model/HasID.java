package com.github.vaatech.aom.commons.basic.persistence.model;

public interface HasID<T> {
    T getId();

    default void setId(T id) {
        throw new UnsupportedOperationException();
    }
}
