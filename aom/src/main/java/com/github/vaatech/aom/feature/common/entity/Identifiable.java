package com.github.vaatech.aom.feature.common.entity;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> extends HasID<T>, HasUID {}
