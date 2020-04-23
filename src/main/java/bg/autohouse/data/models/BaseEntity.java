package bg.autohouse.data.models;

import java.io.Serializable;

abstract class BaseEntity<T extends Serializable> extends BaseAuditEntity
    implements Identifiable<T>, Serializable {

  private static final long serialVersionUID = 1L;
}
