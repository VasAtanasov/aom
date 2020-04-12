package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.ActionLogType;
import java.util.Date;

public interface ActionLog {
  String getId();

  ActionLogType getActionLogType();

  Date getCreatedAt();
}
