package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.ActionLogType;
import java.util.Date;

public interface ActionLog<T> {
  T getId();

  ActionLogType getActionLogType();

  Date getCreatedAt();
}
