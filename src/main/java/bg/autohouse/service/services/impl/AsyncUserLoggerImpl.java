package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.UserLog;
import bg.autohouse.data.models.enums.UserLogType;
import bg.autohouse.data.repositories.UserLogRepository;
import bg.autohouse.service.services.AsyncUserLogger;
import java.util.Arrays;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AsyncUserLoggerImpl implements AsyncUserLogger {

  private final UserLogRepository userLogRepository;

  @Async
  @Override
  public void logUserLogin(String userId) {
    log.info("Recording user login (should be off main thread), user ID : {}", userId);
    userLogRepository.save(new UserLog(userId, UserLogType.USER_SESSION, ""));
  }

  @Async
  @Override
  public void logUserAddPrivateAccount(String userId) {
    userLogRepository.save(new UserLog(userId, UserLogType.USER_ADDED_PRIVATE_ACCOUNT, ""));
  }

  @Async
  @Override
  public void recordUserLog(String userId, UserLogType userLogType, String description) {
    UserLog userLog = new UserLog(userId, userLogType, description);
    userLogRepository.saveAndFlush(userLog);
  }

  @Async
  @Override
  public void storeUserLogs(Set<UserLog> userLogSet) {
    userLogRepository.saveAll(userLogSet);
  }

  @Override
  public void removeAllUserInfoLogs(String userId) {
    userLogRepository.deleteAllByUserIdAndUserLogTypeIn(
        userId,
        Arrays.asList(
            UserLogType.CHANGED_ADDRESS,
            UserLogType.USER_DETAILS_CHANGED,
            UserLogType.USER_EMAIL_CHANGED,
            UserLogType.USER_PHONE_CHANGED));
  }
}
