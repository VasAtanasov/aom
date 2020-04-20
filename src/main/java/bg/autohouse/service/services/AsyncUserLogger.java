package bg.autohouse.service.services;

import bg.autohouse.data.models.UserLog;
import bg.autohouse.data.models.enums.UserLogType;
import java.util.Set;

/** Created by luke on 2016/02/22. */
public interface AsyncUserLogger {

  void logUserLogin(String userId);

  void logUserAddPrivateAccount(String userId);

  void recordUserLog(String userId, UserLogType userLogType, String description);

  void storeUserLogs(Set<UserLog> userLogSet);

  void removeAllUserInfoLogs(String userId);
}
