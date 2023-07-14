package com.github.vaatech.aom.commons.basic.dto;

import com.github.vaatech.aom.commons.basic.persistence.model.Identifiable;

import java.io.Serializable;

public interface BaseDTO<T extends Serializable> extends Serializable, Identifiable<T> {}
