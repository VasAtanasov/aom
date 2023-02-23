package com.github.vaatech.aom.core.model.common;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> extends HasID<T>, HasUID {}
