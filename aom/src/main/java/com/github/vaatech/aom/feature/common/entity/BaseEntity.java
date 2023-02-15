package com.github.vaatech.aom.feature.common.entity;


import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable, Identifiable<T> {}
