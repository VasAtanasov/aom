package bg.autohouse.data.models;

import java.io.Serializable;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import org.springframework.data.domain.Persistable;

public abstract class BaseEntity<T> extends BaseAuditEntity
    implements Identifiable<T>, Persistable<T>, Serializable {

  private static final long serialVersionUID = 1L;

  @Transient private boolean isNew = true;

  @Override
  public boolean isNew() {
    return isNew;
  }

  @PrePersist
  @PostLoad
  void markNotNew() {
    this.isNew = false;
  }
}
