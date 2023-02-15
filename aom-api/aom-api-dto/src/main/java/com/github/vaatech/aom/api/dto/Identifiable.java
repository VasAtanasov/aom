package com.github.vaatech.aom.api.dto;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> extends HasID<T>, HasUID {}
