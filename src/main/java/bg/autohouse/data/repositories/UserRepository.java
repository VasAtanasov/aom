package bg.autohouse.data.repositories;

import bg.autohouse.data.models.User;
import bg.autohouse.data.projections.user.UserIdUsername;
import bg.autohouse.data.projections.user.UserUsername;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

  @Query("SELECT u from User u JOIN FETCH u.roles ur where lower(u.username) = lower(:username)")
  Optional<User> findByUsernameIgnoreCase(@Param("username") String username);

  @Query(
      "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE u.enabled = true ORDER BY u.username")
  List<User> findAllWithRoles();

  @Override
  @Query("SELECT u from User u JOIN FETCH u.roles ur where u.id = :id")
  Optional<User> findById(UUID id);

  boolean existsByUsernameIgnoreCase(String username);

  @Query("SELECT u from User u")
  List<UserIdUsername> getAllUsers();

  @Query("SELECT u from User u")
  Set<UserUsername> getAllUsers(Specification<User> spec);

  // List<Person> findByIdNotIn(List<Long> personIds);
}
