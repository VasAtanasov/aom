package bg.autohouse.data.repositories;

import bg.autohouse.data.models.User;
import bg.autohouse.data.projections.user.UserIdUsername;
import bg.autohouse.data.projections.user.UserUsername;
import bg.autohouse.util.Collect;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

  @Query("SELECT DISTINCT u from User u LEFT JOIN FETCH u.roles r WHERE u.id = :id")
  Optional<User> findByIdWithRoles(UUID id);

  @Query("SELECT u from User u")
  List<UserIdUsername> getAllUsers();

  @Query(value = "SELECT u FROM User u", countQuery = "SELECT COUNT(*) FROM User u")
  Page<User> findUsersPage(Pageable pageable);

  @Query("SELECT u from User u")
  Set<UserUsername> getAllUsers(Specification<User> spec);

  @Query("SELECT u from User u")
  Stream<User> findAllStream(Specification<User> spec);

  @Transactional(readOnly = true)
  default Map<UUID, User> getAllMap(Specification<User> spec) {
    try (Stream<User> userStream = findAllStream(spec)) {
      return userStream.collect(Collect.indexingBy(u -> u.getId()));
    }
  }

  @Modifying
  @Transactional
  int updateHasAccount(Collection<UUID> ids);

  List<User> findAllActiveWithRoles();

  Optional<User> findByUsernameIgnoreCase(String username);

  boolean existsByUsernameIgnoreCase(String username);

  @Query(
      value =
          "SELECT DISTINCT u "
              + "FROM User u "
              + "LEFT JOIN FETCH u.favorites off "
              + "LEFT JOIN FETCH off.vehicle v "
              + "LEFT JOIN FETCH u.filters f "
              + "WHERE u.id = :id")
  Optional<User> findUserById(UUID id);
}
