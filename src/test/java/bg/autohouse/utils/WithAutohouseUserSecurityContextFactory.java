package bg.autohouse.utils;

import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.ExceptionsMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WithAutohouseUserSecurityContextFactory
    implements WithSecurityContextFactory<WithAutohouseUser> {

  private final UserRepository userRepository;

  @Override
  public SecurityContext createSecurityContext(WithAutohouseUser customUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    log.info("======== users count: {}", userRepository.count());
    User user =
        userRepository
            .findByUsernameIgnoreCase(customUser.value())
            .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.NO_SUCH_USERNAME));
    Authentication auth =
        new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }
}
