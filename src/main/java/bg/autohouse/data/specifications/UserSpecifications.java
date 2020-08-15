package bg.autohouse.data.specifications;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.User_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class UserSpecifications {

  public static Specification<User> idIn(Collection<UUID> ids) {
    return (root, query, cb) -> root.get(User_.id).in(ids);
  }

  public static Specification<User> usernameIn(Collection<String> usernames) {
    return (root, query, cb) -> root.get(User_.username).in(usernames);
  }

  public static Specification<User> hasNoAccount() {
    return (root, query, cb) -> cb.isFalse(root.get(User_.hasAccount));
  }
}
