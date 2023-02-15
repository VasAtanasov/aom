package com.github.vaatech.aom.feature.common.entity;


import java.io.Serializable;

public interface BaseDTO<T extends Serializable> extends Serializable, Identifiable<T> {}
