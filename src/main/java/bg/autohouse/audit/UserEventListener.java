package bg.autohouse.audit;

import bg.autohouse.audit.models.UserLoginEvent;
import bg.autohouse.audit.models.UserLogoutEvent;
import bg.autohouse.service.services.AsyncUserLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {

  private final AsyncUserLogger userLogger;

  @Async
  @EventListener
  void handleLoginEvent(UserLoginEvent event) {
    log.info("Logging user register event");
    userLogger.logUserLogin(event.getUserId());
  }

  @Async
  @EventListener
  void handleLogoutEvent(UserLogoutEvent event) {
    log.info("Logging user register event");
    userLogger.auditLogoutEvent(event.getUserId());
  }
}
