package bg.autohouse.data.specifications;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.User_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

  public static Specification<User> isEnabled() {
    return (root, query, cb) -> cb.isTrue(root.get(User_.enabled));
  }
}
