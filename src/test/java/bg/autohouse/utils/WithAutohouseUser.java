package bg.autohouse.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAutohouseUserSecurityContextFactory.class)
public @interface WithAutohouseUser {

  String value();
}
