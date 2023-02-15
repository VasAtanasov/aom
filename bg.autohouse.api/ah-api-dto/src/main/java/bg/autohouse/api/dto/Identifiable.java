package bg.autohouse.api.dto;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> extends HasID<T>, HasUID {}
