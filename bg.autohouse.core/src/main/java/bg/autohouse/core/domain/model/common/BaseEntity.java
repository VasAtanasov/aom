package bg.autohouse.core.domain.model.common;

import org.springframework.data.domain.Persistable;

import javax.persistence.Transient;
import java.io.Serializable;

/** Base entity interface which holds preCreate and preUpdate functions to set base fields. */
public interface BaseEntity<T extends Serializable>
    extends Serializable, HasID<T>, Persistable<T> {

  @Transient
  default boolean isNew() {
    return null == getId();
  }


}
