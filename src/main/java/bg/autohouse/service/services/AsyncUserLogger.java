package bg.autohouse.service.services;

import bg.autohouse.data.models.UserLog;
import bg.autohouse.data.models.enums.UserLogType;
import java.util.Set;
import java.util.UUID;

/** Created by luke on 2016/02/22. */
public interface AsyncUserLogger {

  void logUserLogin(UUID userId);

  void logUserAddPrivateAccount(UUID userId);

  void logUserAddDealerAccount(UUID userId);

  void recordUserLog(UUID userId, UserLogType userLogType, String description);

  void storeUserLogs(Set<UserLog> userLogSet);

  void removeAllUserInfoLogs(UUID userId);

  void auditLogoutEvent(UUID userId);
}
