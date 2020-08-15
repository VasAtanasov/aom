package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.UserLog;
import bg.autohouse.data.models.enums.UserLogType;
import bg.autohouse.data.repositories.UserLogRepository;
import bg.autohouse.service.services.AsyncUserLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AsyncUserLoggerImpl implements AsyncUserLogger {

  private final UserLogRepository userLogRepository;

  @Async
  @Override
  public void logUserLogin(UUID userId) {
    log.info("Recording user login (should be off main thread), user ID : {}", userId);
    userLogRepository.save(new UserLog(userId, UserLogType.USER_SESSION, ""));
  }

  @Async
  @Override
  public void logUserAddPrivateAccount(UUID userId) {
    userLogRepository.save(new UserLog(userId, UserLogType.USER_ADDED_PRIVATE_ACCOUNT, ""));
  }

  @Async
  @Override
  public void logUserAddDealerAccount(UUID userId) {
    userLogRepository.save(new UserLog(userId, UserLogType.USER_ADDED_DEALER_ACCOUNT, ""));
  }

  @Async
  @Override
  public void recordUserLog(UUID userId, UserLogType userLogType, String description) {
    UserLog userLog = new UserLog(userId, userLogType, description);
    userLogRepository.saveAndFlush(userLog);
  }

  @Async
  @Override
  public void auditLogoutEvent(UUID userId) {
    userLogRepository.save(new UserLog(userId, UserLogType.USER_LOGOUT_SESSION, ""));
  }
}
