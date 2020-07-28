package bg.autohouse.data.specifications;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.User_;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
// import javax.persistence.criteria.CriteriaQuery;
// import javax.persistence.criteria.JoinType;
// import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

  public static Specification<User> isEnabled() {
    return (root, query, cb) -> cb.isTrue(root.get(User_.enabled));
  }

  public static Specification<User> createdAfter(Date start) {
    return (root, query, cb) -> cb.greaterThan(root.get(User_.createdAt), start);
  }

  public static Specification<User> createdBefore(Date end) {
    return (root, query, cb) -> cb.lessThan(root.get(User_.createdAt), end);
  }

  public static Specification<User> idIn(Collection<UUID> ids) {
    return (root, query, cb) -> root.get(User_.id).in(ids);
  }

  public static Specification<User> usernameIn(Collection<String> usernames) {
    return (root, query, cb) -> root.get(User_.username).in(usernames);
  }

  public static Specification<User> hasNoAccount() {
    return (root, query, cb) -> cb.isFalse(root.get(User_.hasAccount));
  }

  // private static void performJoins(Root<User> root, CriteriaQuery<?> query) {
  //   if (currentQueryIsCountRecords(query)) {
  //     root.join(User_.roles, JoinType.LEFT);
  //   } else {
  //     root.fetch(User_.roles, JoinType.LEFT);
  //   }
  // }

  // private static boolean currentQueryIsCountRecords(CriteriaQuery<?> criteriaQuery) {
  //   return criteriaQuery.getResultType() == Long.class
  //       || criteriaQuery.getResultType() == long.class;
  // }
}
