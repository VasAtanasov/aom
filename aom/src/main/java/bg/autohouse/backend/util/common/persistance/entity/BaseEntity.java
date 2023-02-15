package bg.autohouse.backend.util.common.persistance.entity;

import bg.autohouse.api.dto.Identifiable;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> extends Serializable, Identifiable<T> {}
