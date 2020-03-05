package bg.autohouse.data.models;

import java.io.Serializable;

public interface EntityDetails extends Serializable {

  boolean isDeleted();

  boolean isExpired();

  boolean isActive();
}
