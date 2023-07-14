package com.github.vaatech.aom.commons.basic.persistence.model;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> extends HasID<T>, HasUID {}
