package com.github.vaatech.aom.commons.basic.persistence.model;

import java.util.UUID;

public interface HasVersion {
    default UUID getVersion() {
        throw new UnsupportedOperationException();
    }

    default void setVersion(UUID version) {
        throw new UnsupportedOperationException();
    }
}
