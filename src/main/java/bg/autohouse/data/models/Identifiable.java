package bg.autohouse.data.models;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {

  T getId();
}
