package com.github.vaatech.aom.core.model.common;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable, Identifiable<T> {}
