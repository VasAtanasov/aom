package bg.autohouse.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Component
@EnableJpaAuditing
public class AuditConfiguration implements AuditorAware<String> {
  public static final String ANONYMOUS_USER = "anonymoususer";
  public static final String SYSTEM_ACCOUNT = "system";

  @Override
  public Optional<String> getCurrentAuditor() {
    // TODO ADD SecurityUtils when spring security is added
    // return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(SYSTEM_ACCOUNT));
    return Optional.of(SYSTEM_ACCOUNT);
  }
}
