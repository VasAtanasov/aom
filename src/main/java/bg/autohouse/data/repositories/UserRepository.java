package bg.autohouse.data.repositories;

import bg.autohouse.data.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u from User u where lower(u.username) = lower(:username)")
  Optional<User> findByUsernameIgnoreCase(@Param("username") String username);

  boolean existsByUsernameIgnoreCase(String username);

  boolean existsByEmailIgnoreCase(String email);
}
