package bg.autohouse.util.common.persistance.entity;

import bg.autohouse.util.common.Identifiable;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable, Identifiable<T> {}
