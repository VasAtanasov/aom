package com.github.vaatech.aom.util.common.persistance.entity;

import com.github.vaatech.aom.api.dto.Identifiable;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable, Identifiable<T> {}
