package bg.autohouse.service.services;

import bg.autohouse.data.models.enums.UserLogType;

import java.util.UUID;

public interface AsyncUserLogger {

  void logUserLogin(UUID userId);

  void logUserAddPrivateAccount(UUID userId);

  void logUserAddDealerAccount(UUID userId);

  void recordUserLog(UUID userId, UserLogType userLogType, String description);

  void auditLogoutEvent(UUID userId);
}
