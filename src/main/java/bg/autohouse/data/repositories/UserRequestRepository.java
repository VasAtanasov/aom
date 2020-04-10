package bg.autohouse.data.repositories;

import bg.autohouse.data.models.UserCreateRequest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepository extends JpaRepository<UserCreateRequest, String> {
  boolean existsByUsernameIgnoreCase(String username);

  Optional<UserCreateRequest> findByUsername(String username);

  Optional<UserCreateRequest> findByUsernameAndIsVerifiedIsFalse(String username);
}
