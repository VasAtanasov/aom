package com.github.vaatech.aom.commons.basic.persistence.model;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable, Identifiable<T> {}
