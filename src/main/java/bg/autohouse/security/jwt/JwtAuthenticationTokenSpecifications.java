package bg.autohouse.security.jwt;

import org.springframework.data.jpa.domain.Specification;

public class JwtAuthenticationTokenSpecifications {

  public static Specification<JwtAuthenticationToken> forUser(String username) {
    return (root, query, cb) -> cb.equal(root.get(JwtAuthenticationToken_.username), username);
  }

  public static Specification<JwtAuthenticationToken> withValue(String value) {
    return (root, query, cb) -> cb.equal(root.get(JwtAuthenticationToken_.value), value);
  }
}
