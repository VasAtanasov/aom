package bg.autohouse.util.common.persistance.entity;

import bg.autohouse.util.common.datetime.DateTimeUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/** Base entity interface which holds preCreate and preUpdate functions to set base fields. */
public interface BaseLifecycleEntity<T extends Serializable>
    extends PreProcessed, Serializable, BaseEntity<T>
{

  default void preCreate() {
    setCreatedAt(DateTimeUtils.currentInUtc());
    UUID uuid = UUID.randomUUID();
    setUID(uuid);
    setVersion(uuid);
  }

  default void preUpdate() {
    setUpdatedAt(DateTimeUtils.currentInUtc());
    setVersion(UUID.randomUUID());
  }

  @Override
  void setUID(UUID uid);

  void setCreatedAt(ZonedDateTime date);

  void setCreatedBy(String createdBy);

  void setUpdatedAt(ZonedDateTime date);

  void setUpdatedBy(String updatedBy);

  void setDeletedAt(ZonedDateTime deletedBy);

  void setDeletedBy(String deletedBy);
}
