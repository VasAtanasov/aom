package bg.autohouse.data.repositories;

import bg.autohouse.data.models.UserCreateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRequestRepository extends JpaRepository<UserCreateRequest, UUID> {

  Optional<UserCreateRequest> findByUsernameAndIsVerifiedIsFalse(String username);
}
