package bg.autohouse.api.domain.model.common;

import java.time.LocalDateTime;

public interface Auditable {
  LocalDateTime createdAt();

  LocalDateTime updatedAt();

  String createdBy();

  String modifiedBy();
}
