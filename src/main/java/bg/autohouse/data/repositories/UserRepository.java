package bg.autohouse.data.repositories;

import bg.autohouse.data.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u from User u JOIN FETCH u.roles ur where lower(u.username) = lower(:username)")
  Optional<User> findByUsernameIgnoreCase(@Param("username") String username);

  @Query(
      "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE u.enabled = true ORDER BY u.username")
  List<User> findAllWithRoles();

  @Override
  @Query("SELECT u from User u JOIN FETCH u.roles ur where u.id = :id")
  Optional<User> findById(String id);

  Optional<User> findByEmail(String email);

  boolean existsByUsernameIgnoreCase(String username);

  boolean existsByEmailIgnoreCase(String email);
}
