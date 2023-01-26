package bg.autohouse.util.common.dto;

import bg.autohouse.util.common.Identifiable;

import java.io.Serializable;

public interface BaseDTO<T extends Serializable> extends Serializable, Identifiable<T> {}
