package bg.autohouse.data.specifications;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.UserLog;
import bg.autohouse.data.models.UserLog_;
import bg.autohouse.data.models.enums.UserLogType;
import java.util.Date;
import org.springframework.data.jpa.domain.Specification;

/** Created by luke on 2017/04/24. */
public final class UserLogSpecifications {

  public static Specification<UserLog> forUser(User user) {
    return (root, query, cb) -> cb.equal(root.get(UserLog_.userId), user.getId());
  }

  public static Specification<UserLog> forUser(String userId) {
    return (root, query, cb) -> cb.equal(root.get(UserLog_.userId), userId);
  }

  public static Specification<UserLog> ofType(UserLogType type) {
    return (root, query, cb) -> cb.equal(root.get(UserLog_.userLogType), type);
  }

  public static Specification<UserLog> creationTimeBetween(Date start, Date end) {
    return (root, query, cb) -> cb.between(root.get(UserLog_.createdAt), start, end);
  }

  public static Specification<UserLog> hasDescription(String description) {
    return (root, query, cb) -> cb.equal(root.get(UserLog_.description), description);
  }
}
