package bg.autohouse.util.common;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> extends HasID<T>, HasUID {}
