package com.github.vaatech.aom.api.dto;


import java.io.Serializable;

public interface BaseDTO<T extends Serializable> extends Serializable, Identifiable<T> {}
