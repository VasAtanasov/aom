package bg.autohouse.core.domain.model.common;

import java.util.UUID;

public interface HasID<T> {
  T getId();

  UUID getUid();
}
